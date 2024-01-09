package com.SEApp.app.model.persist.utils;

public class UpdateOperand<T> {

        private String column;
        private T value;

        public UpdateOperand(String column, T value) {
            this.column = column;
            this.value = value;
        }

        public String getColumn() {
            return column;
        }
        public T getValue() {
            return value;
        }

        public String toString() {
            return column + " = " + value;
        }

        public String toPreparedString() {
            return column + " = ?";
        }

        public void setColumn(String column) {
            this.column = column;
        }
        public void setValue(T value) {
            this.value = value;
        }
}

/**
 *
 */