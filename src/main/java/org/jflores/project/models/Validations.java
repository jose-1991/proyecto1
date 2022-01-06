package org.jflores.project.models;

public enum Validations {
    CUSTOMER("customer name"),
    PRODUCT("product name"),
    QUANTITY("quantity"),
    PRICE("price"),
    DISCOUNT("discount"),
    POSTAL_CODE("postal code");
    final String code;

    Validations(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}