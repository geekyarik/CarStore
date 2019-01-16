package com.softserve.yaroslav.items.dto;

import com.softserve.yaroslav.items.entity.FieldNames;

public class SearchStatementDto {

    private String fieldName;
    private String regex;

    /**
     * @param fieldName
     * @param regex
     */
    public SearchStatementDto(String fieldName, String regex) {
	super();
	this.fieldName = fieldName;
	this.regex = regex;
    }

    // Getters
    public String getFieldName() {
	return fieldName;
    }

    public String getRegex() {
	if(fieldName.equals(FieldNames.ID) ||
		fieldName.equals(FieldNames.IS_ACTIVE) ||
		fieldName.equals(FieldNames.IS_ADMIN) ||
		fieldName.equals(FieldNames.ID_USER) ||
		fieldName.equals(FieldNames.ENGINE_CAPACITY) ||
		fieldName.equals(FieldNames.YEAR) ||
		fieldName.equals(FieldNames.MILEAGE) ||
		fieldName.equals(FieldNames.PRICE)) {
	    return regex; 
	} else {
	    return "'%" + regex + "%'";
	}
    }

    // Setters
    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    public void setRegex(String regex) {
	this.regex = regex;
    }

}
