package com.SEApp.app.model.persist.utils;

public class WhereOperand {

    private String column;
    private String value;
    private String operator;

    public WhereOperand(String column, String operator, String value) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public String getColumn() {
        return column;
    }
    public String getValue() {
        return value;
    }
    public String getOperator() {
        return operator;
    }

    public String toString() {
        return column + " " + operator + " " + value;
    }

    public String toPreparedString() {
        return column + " " + operator + " ?";
    }

    public void setColumn(String column) {
        this.column = column;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
