package com.restdatabus.dao.jdbc.sql;

public class SqlConstants {

    private SqlConstants() {}

    public static final String ID_FIELD = "id";

    public static final String OWNER = "restdatabus";

    public static final String TABLE_PREFIX = "entity_";
    public static final String FIELD_PREFIX = "field_";
    public static final String FK_PREFIX = "fk_";
    public static final String FK_IDX_PREFIX = "fki_";


    public static String fieldName(Long fieldDefinitionId) {
        return FIELD_PREFIX + fieldDefinitionId;
    }

    public static String tableName(Long entityDefinitionId) {
        return TABLE_PREFIX + entityDefinitionId;
    }

    public static String foreignKeyName(String tableName, String fieldName, String targetTable) {
        return FK_PREFIX + relationName(tableName, fieldName, targetTable);
    }
    public static String foreignKeyIndexName(String tableName, String fieldName, String targetTable) {
        return FK_IDX_PREFIX + relationName(tableName, fieldName, targetTable);
    }
    public static String relationName(String tableName, String fieldName, String targetTable) {
        return tableName + "_" + fieldName + "_" + targetTable;
    }
}
