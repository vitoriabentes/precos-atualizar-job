package precos.atualizar.job.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import precos.atualizar.job.interfaces.AtivoRepository;
import precos.atualizar.job.interfaces.AtivoService;
import precos.atualizar.job.models.Ativo;

@Service
@Transactional
public class AtivoServiceImpl implements AtivoService {
    private static final Logger log = LoggerFactory.getLogger(AtivoServiceImpl.class);

    @Autowired
    private AtivoRepository ativoRepository;

    public void updateQuantityAtivo(int quantityTraded, String codigoAtivo) {
        Ativo ativo = ativoRepository.findByCodigo(codigoAtivo);
        int newQuantity = ativo.getQuantidade() - quantityTraded;

        log.info("Atualizando quantidade do ativo {}: de {} para {}", codigoAtivo, ativo.getQuantidade(), newQuantity);
        ativoRepository.updateQuantityAtivo(newQuantity, codigoAtivo);
    }
}
