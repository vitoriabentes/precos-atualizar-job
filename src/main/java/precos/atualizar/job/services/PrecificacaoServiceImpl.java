package precos.atualizar.job.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import precos.atualizar.job.enums.TypeOfOperacao;
import precos.atualizar.job.interfaces.PrecificacaoRepository;
import precos.atualizar.job.interfaces.PrecificacaoService;
import precos.atualizar.job.models.MessageOperacao;
import precos.atualizar.job.models.Precificacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PrecificacaoServiceImpl implements PrecificacaoService {
    private static final Logger log = LoggerFactory.getLogger(PrecificacaoServiceImpl.class);

    @Autowired
    private PrecificacaoRepository precificacaoRepository;

    @Override
    public void updateAtivoPrecificacao(MessageOperacao messageOperacao) {
        try{
            Precificacao lastRegistration = precificacaoRepository.findLastByCodigoAtivo(messageOperacao.getCodigoAtivo());

            log.info("Desatualizando último registro do ativo {}", messageOperacao.getCodigoAtivo());
            precificacaoRepository.updateOutdatedPrecificao(lastRegistration.getId());

            Precificacao precificacaoUpdate = calculePrecificacao(messageOperacao);

            log.info("Registro nova cotação do ativo: {}", precificacaoUpdate.getCodigoAtivo());
            precificacaoRepository.registerNewPrecificacao(precificacaoUpdate);
        }catch (RuntimeException e) {
            String message = "Erro ao tentar atualizar precificação para o ativo: " + messageOperacao.getCodigoAtivo();
            log.error("{}. Mensagem de exceção: {}", message,  e.getMessage());
            throw new RuntimeException(message);
        }
    }

    private Precificacao calculePrecificacao(MessageOperacao messageOperacao){
        BigDecimal constant = messageOperacao.getIndexador().multiply(BigDecimal.valueOf(messageOperacao.getQuantidade()));
        if(messageOperacao.getTipoOperacao().equals(TypeOfOperacao.VENDA)){
            constant = constant.multiply(BigDecimal.valueOf(-1));
        }
        BigDecimal preco = messageOperacao.getPrecoUnitario().add(messageOperacao.getPrecoUnitario().multiply(constant));

        log.info("Calculando nova precificação para o ativo: {}. Preço calculado: {}", messageOperacao.getCodigoAtivo(), preco);

        return new Precificacao(
                messageOperacao.getCodigoAtivo(),
                preco,
                LocalDateTime.now(),
                true
        );
    }
}
