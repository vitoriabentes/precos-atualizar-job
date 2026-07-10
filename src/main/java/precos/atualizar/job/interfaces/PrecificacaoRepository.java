package precos.atualizar.job.interfaces;

import org.springframework.stereotype.Component;
import precos.atualizar.job.models.Precificacao;

@Component
public interface PrecificacaoRepository {
    Precificacao findLastByCodigoAtivo(String codigoAtivo);
    void updateOutdatedPrecificao(int idPrecificacao);
    void registerNewPrecificacao(Precificacao precificacaoUpdate);
}
