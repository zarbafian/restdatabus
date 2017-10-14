package com.restdatabus.model.data;

/**
 * The type of data.
 */
public enum DataType {

    /**
     * List 1 / 2, if you edit this list edit the also List 2 / 2 accordingly.
     */
    YESNO("data-type.yesno"),
    TEXT("data-type.text"),
    PARAGRAPH("data-type.paragraph"),
    INTEGER("data-type.integer"),
    DECIMAL("data-type.decimal"),
    LIST("data-type.list"),
    ENTITY("data-type.entity");

    private String key;

    DataType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static DataType parse(String key) {
        switch (key) {
            /**
             * List 2 / 2
             */
            case "data-type.yesno":
                return YESNO;
            case "data-type.paragraph":
                return PARAGRAPH;
            case "data-type.integer":
                return INTEGER;
            case "data-type.decimal":
                return DECIMAL;
            case "data-type.list":
                return LIST;
            case "data-type.entity":
                return ENTITY;
            case "data-type.text":
                default:
                    return TEXT;
        }
    }

    @Override
    public String toString() {
        return "DataType{" +
                "key='" + key + '\'' +
                '}';
    }
}
