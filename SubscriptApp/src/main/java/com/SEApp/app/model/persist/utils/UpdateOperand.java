package com.SEApp.app.model.persist.utils;

public class UpdateOperand {

        private String column;
        private String value;

        public UpdateOperand(String column, String value) {
            this.column = column;
            this.value = value;
        }

        public String getColumn() {
            return column;
        }
        public String getValue() {
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
        public void setValue(String value) {
            this.value = value;
        }
}
