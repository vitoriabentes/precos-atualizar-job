package precos.atualizar.job.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Precificacao{
    private int id;
    private String codigoAtivo;
    private BigDecimal preco;
    private LocalDateTime dataHoraAtualizacao;
    private boolean atualizado;

    public Precificacao(String codigoAtivo, BigDecimal preco, LocalDateTime dataHoraAtualizacao, boolean atualizado) {
        this.codigoAtivo = codigoAtivo;
        this.preco = preco;
        this.dataHoraAtualizacao = dataHoraAtualizacao;
        this.atualizado = atualizado;
    }
}
