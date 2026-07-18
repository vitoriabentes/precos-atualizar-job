package precos.atualizar.job.repositorys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import precos.atualizar.job.interfaces.AtivoRepository;
import precos.atualizar.job.mappers.AtivoRowMapper;
import precos.atualizar.job.models.Ativo;


@Repository
public class AtivoRepositoryImpl implements AtivoRepository {
    private static final Logger log = LoggerFactory.getLogger(AtivoRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AtivoRowMapper ativoRowMapper;

    @Override
    public void updateQuantityAtivo(int newQuantity, String codigoAtivo) {
        String query = """
                UPDATE CADASTROS.ATIVO 
                SET QUANTIDADE = ?
                WHERE CODIGO = ?
                """;
        jdbcTemplate.update(query, newQuantity, codigoAtivo);
    }

    @Override
    public Ativo findByCodigo(String codigo) {
        String query = """
                SELECT * FROM CADASTROS.ATIVO WHERE CODIGO = ?
                """;
        return jdbcTemplate.queryForObject(query, ativoRowMapper, codigo);
    }

}
