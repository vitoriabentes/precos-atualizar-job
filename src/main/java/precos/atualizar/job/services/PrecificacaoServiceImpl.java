package precos.atualizar.job.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import precos.atualizar.job.enums.TypeOfOperacao;
import precos.atualizar.job.interfaces.PrecificacaoRepository;
import precos.atualizar.job.interfaces.PrecificacaoService;
import precos.atualizar.job.models.MessageOperacao;
import precos.atualizar.job.models.Precificacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@Transactional
public class PrecificacaoServiceImpl implements PrecificacaoService {
    private static final Logger log = LoggerFactory.getLogger(PrecificacaoServiceImpl.class);

    @Autowired
    private PrecificacaoRepository precificacaoRepository;

    @Override
    public void updateAtivoPrecificacao(MessageOperacao messageOperacao) {
        try {
            Precificacao lastRegistration = precificacaoRepository.findLastByCodigoAtivo(messageOperacao.getCodigoAtivo())
                    .orElseThrow(() -> new RuntimeException("Ativo com precificação não encontrada: " + messageOperacao.getCodigoAtivo()));

            log.info("Desatualizando último registro do ativo {}", messageOperacao.getCodigoAtivo());
            precificacaoRepository.updateOutdatedPrecificao(lastRegistration.getId());

            Precificacao precificacaoUpdate = calculePrecificacao(messageOperacao);

            log.info("Registro nova cotação do ativo: {}", precificacaoUpdate.getCodigoAtivo());
            precificacaoRepository.registerNewPrecificacao(precificacaoUpdate);
        } catch (RuntimeException e) {
            String message = "Erro ao tentar atualizar precificação para o ativo: " + messageOperacao.getCodigoAtivo();
            log.error("{}. Mensagem de exceção: {}", message,  e.getMessage());
            throw new RuntimeException(message);
        }
    }

    private Precificacao calculePrecificacao(MessageOperacao messageOperacao){
        BigDecimal oldPrice = messageOperacao.getPrecoUnitario();
        BigDecimal quantityFactor = BigDecimal.valueOf(Math.sqrt(messageOperacao.getQuantidade()));
        int signal = messageOperacao.getTipoOperacao().equals(TypeOfOperacao.VENDA) ? -1 : 1;

        BigDecimal impact = messageOperacao.getIndexador()
                .multiply(quantityFactor)
                .multiply(BigDecimal.valueOf(signal));

        log.info("Realizando calculo da nova precificacao do ativo {}: {} × (1 + {} × √{} × {})",
                messageOperacao.getCodigoAtivo(),
                oldPrice.setScale(6, RoundingMode.DOWN),
                messageOperacao.getIndexador(),
                messageOperacao.getQuantidade(),
                signal
        );

        BigDecimal newPrice = oldPrice.add(oldPrice.multiply(impact))
                .setScale(6, RoundingMode.DOWN);

        log.info("Precificacao atualizada para o ativo {}: R$ {} -> R$ {} (variacao de {}%)",
                messageOperacao.getCodigoAtivo(),
                oldPrice.setScale(6, RoundingMode.DOWN),
                newPrice,
                impact.multiply(BigDecimal.valueOf(100)).setScale(6, RoundingMode.DOWN)
        );

        return new Precificacao(
                messageOperacao.getCodigoAtivo(),
                newPrice,
                LocalDateTime.now(),
                true
        );
    }
}
