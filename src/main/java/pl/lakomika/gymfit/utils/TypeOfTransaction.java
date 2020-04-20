package pl.lakomika.gymfit.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfTransaction {

    BANK_TRANSFER("Przelew Bankowy"),

    IN_CLUB("Płatność w klubie"),
    ;

    private final String value;

    TypeOfTransaction(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
