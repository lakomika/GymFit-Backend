package pl.lakomika.gymfit.utils;

import com.fasterxml.jackson.annotation.JsonValue;


public enum InvoiceStatus {

    UNPAID("NIEZAPŁACONE"),
    PAID("ZAPŁACONE"),
    CANCELLED("ANULOWANE");

    private final String value;

    InvoiceStatus(String value) {
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
