package precos.atualizar.job.interfaces;

import precos.atualizar.job.enums.TypeOfOperacao;

public interface AtivoService {
    void updateQuantityAtivo(int quantityTraded , String codigoAtivo, TypeOfOperacao type);
}
