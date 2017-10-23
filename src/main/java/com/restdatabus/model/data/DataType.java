package com.restdatabus.model.data;

/**
 * The type of data.
 */
public enum DataType {

    /**
     * List 1 / 2, if you edit this list edit the also List 2 / 2 accordingly.
     */
    YESNO("yesno"),
    TEXT("text"),
    PARAGRAPH("paragraph"),
    INTEGER("integer"),
    DECIMAL("decimal"),
    LIST("list"),
    ENTITY("entity"),
    FILE("file");

    private String key;

    DataType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static DataType fromKey(String key) {
        switch (key) {
            /**
             * List 2 / 2
             */
            case "yesno":
                return YESNO;
            case "paragraph":
                return PARAGRAPH;
            case "integer":
                return INTEGER;
            case "decimal":
                return DECIMAL;
            case "list":
                return LIST;
            case "entity":
                return ENTITY;
            case "text":
                return TEXT;
            case "file":
                return FILE;

                default: throw new IllegalArgumentException("unknown data type '" + key + "'");
        }
    }

    @Override
    public String toString() {
        return "DataType{" +
                "key='" + key + '\'' +
                '}';
    }
}
