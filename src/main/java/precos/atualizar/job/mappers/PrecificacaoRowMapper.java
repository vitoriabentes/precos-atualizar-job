package precos.atualizar.job.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import precos.atualizar.job.models.Precificacao;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PrecificacaoRowMapper implements RowMapper<Precificacao> {

    @Override
    public Precificacao mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Precificacao(
                rs.getInt("ID"),
                rs.getString("CODIGO_ATIVO"),
                rs.getBigDecimal("PRECO"),
                rs.getTimestamp("DATA_HORA_ATUALIZACAO").toLocalDateTime(),
                rs.getBoolean("ATUALIZADO")
        );
    }
}
