package pl.lakomika.gymfit.utils;

import com.fasterxml.jackson.annotation.JsonValue;


public enum Role {

    ADMIN("ROLE_ADMIN"),

    RECEPTIONIST("ROLE_RECEPTIONIST"),

    CLIENT("ROLE_CLIENT");

    private final String value;

    Role(String value) {
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
