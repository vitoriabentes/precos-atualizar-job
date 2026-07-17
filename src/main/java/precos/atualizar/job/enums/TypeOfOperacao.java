package precos.atualizar.job.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfOperacao {
    COMPRA("COMPRA"),
    VENDA("VENDA");

    private String value;

    TypeOfOperacao(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TypeOfOperacao fromValue(String value) {
        for (TypeOfOperacao type : TypeOfOperacao.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de operação inválida: " + value);
    }
}
