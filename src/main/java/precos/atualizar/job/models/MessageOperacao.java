package precos.atualizar.job.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import precos.atualizar.job.enums.TypeOfOperacao;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class MessageOperacao {
    @JsonProperty("codigo_ativo")
    private String codigoAtivo;

    private int quantidade;

    @JsonProperty("preco_unitario")
    private BigDecimal precoUnitario;

    private BigDecimal indexador;

    @JsonProperty("tipo_operacao")
    private TypeOfOperacao tipoOperacao;

}
