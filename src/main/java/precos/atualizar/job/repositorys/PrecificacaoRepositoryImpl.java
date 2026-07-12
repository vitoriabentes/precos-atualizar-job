package precos.atualizar.job.repositorys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import precos.atualizar.job.interfaces.PrecificacaoRepository;
import precos.atualizar.job.mappers.PrecificacaoRowMapper;
import precos.atualizar.job.models.Precificacao;

@Repository
public class PrecificacaoRepositoryImpl implements PrecificacaoRepository {
    private static final Logger log = LoggerFactory.getLogger(PrecificacaoRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PrecificacaoRowMapper precificacaoRowMapper;

    @Override
    public Precificacao findLastByCodigoAtivo(String codigoAtivo) {
        String query = """
                SELECT * FROM PRECOS.PRECIFICACAO WHERE ATUALIZADO = TRUE AND CODIGO_ATIVO = ? 
                """;
        return jdbcTemplate.queryForObject(query, precificacaoRowMapper, codigoAtivo);
    }

    @Override
    public void updateOutdatedPrecificao(int idPrecificacao) {
        String query = """
                UPDATE PRECOS.PRECIFICACAO SET ATUALIZADO = false WHERE ID = ?
                """;
        jdbcTemplate.update(query, idPrecificacao);
    }

    @Override
    public void registerNewPrecificacao(Precificacao precificacaoUpdate) {
        String query = """
                INSERT INTO PRECOS.PRECIFICACAO (CODIGO_ATIVO, PRECO, DATA_HORA_ATUALIZACAO, ATUALIZADO)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(query,
                precificacaoUpdate.getCodigoAtivo(),
                precificacaoUpdate.getPreco(),
                precificacaoUpdate.getDataHoraAtualizacao(),
                precificacaoUpdate.isAtualizado());
    }
}
