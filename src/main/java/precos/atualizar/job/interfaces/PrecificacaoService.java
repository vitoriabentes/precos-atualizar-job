package precos.atualizar.job.interfaces;

import org.springframework.stereotype.Component;
import precos.atualizar.job.models.MessageOperacao;

@Component
public interface PrecificacaoService {
    void updateAtivoPrecificacao(MessageOperacao messageOperacao);
}
