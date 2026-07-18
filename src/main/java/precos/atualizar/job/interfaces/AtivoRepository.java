package precos.atualizar.job.interfaces;

import org.springframework.stereotype.Component;
import precos.atualizar.job.models.Ativo;

@Component
public interface AtivoRepository {
    void updateQuantityAtivo(int newQuantity, String codigoAtivo);
    Ativo findByCodigo(String codigo);
}
