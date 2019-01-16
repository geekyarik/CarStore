package com.softserve.yaroslav.items.entity;

public enum FieldNames {

    ID("id"),
    BRAND("brand"),
    MODEL("model"),
    GEARBOX("gearbox"),
    DETAILS("details"),
    ID_USER("id_user"),
    ENGINE_CAPACITY("engine_capacity"),
    YEAR("year"),
    MILEAGE("mileage"),
    PRICE("price"),
    USERNAME("username"),
    FIRSTNAME("firstname"),
    SECONDNAME("secondname"),
    LOGIN("login"),
    PASSWORD("password"),
    EMAIL("email"),
    PHONE("phone"),
    IS_ACTIVE("is_active"),
    IS_ADMIN("is_admin");
    
    private String name;

    private FieldNames(String name) {
	    this.name = name;
    }

    @Override
    public String toString() {
	return name;
    }
}
