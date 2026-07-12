package precos.atualizar.job.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import precos.atualizar.job.interfaces.PrecificacaoService;
import precos.atualizar.job.models.MessageOperacao;

@Component
public class PrecosAtualizarConsumer {
    private static final Logger log = LoggerFactory.getLogger(PrecosAtualizarConsumer.class);

    @Autowired
    private PrecificacaoService precificacaoService;

    @SqsListener("${app.sqs.queue-name}")
    public void listen(@Payload MessageOperacao message) {
        log.info("Mensagem das {} recebida, atualizando precificação do ativo: {}", message.getCodigoAtivo(), message.getDataHoraCriacao());
        precificacaoService.updateAtivoPrecificacao(message);
    }

}
