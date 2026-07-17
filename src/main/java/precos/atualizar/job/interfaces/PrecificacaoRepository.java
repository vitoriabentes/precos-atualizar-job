package precos.atualizar.job.interfaces;

import org.springframework.stereotype.Component;
import precos.atualizar.job.models.Precificacao;

import java.util.Optional;

@Component
public interface PrecificacaoRepository {
    Optional<Precificacao> findLastByCodigoAtivo(String codigoAtivo);
    void updateOutdatedPrecificao(int idPrecificacao);
    void registerNewPrecificacao(Precificacao precificacaoUpdate);
}
