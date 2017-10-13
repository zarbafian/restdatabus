package com.restdatabus.model.data;

/**
 * The type of data.
 */
public enum DataType {

    YESNO("datatype.yesno"),
    TEXT("datatype.text"),
    PARAGRAPH("datatype.paragraph"),
    INTEGER("datatype.integer"),
    DECIMAL("datatype.decimal"),
    LIST("datatype.list"),
    ENTITY("datatype.entity");

    private String key;

    DataType(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "DataType{" +
                "key='" + key + '\'' +
                '}';
    }
}
