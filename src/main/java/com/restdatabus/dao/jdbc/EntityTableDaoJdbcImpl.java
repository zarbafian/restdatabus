package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EntityTableDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EntityTableDaoJdbcImpl implements EntityTableDao {

    private static final String OWNER = "restdatabus";

    private static final String TABLE_PREFIX = "entity_";
    private static final String FIELD_PREFIX = "field_";
    private static final String ID_FIELD = "id";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EntityTableDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTable(EntityDefinition entityDefinition) {

        if(entityDefinition.getId() == null) {
            throw new IllegalArgumentException("entity has no id");
        }

        String tableName = tableName(entityDefinition.getId());

        jdbcTemplate.execute(
                "CREATE TABLE " + tableName + " (" +
                        ID_FIELD + " bigserial NOT NULL, " +
                        "CONSTRAINT pk_" + tableName + " PRIMARY KEY (" + ID_FIELD + ") " +
                        ") WITH ( OIDS=FALSE )"
        );
        jdbcTemplate.execute(
                "ALTER TABLE " + tableName + " OWNER TO " + OWNER
        );
    }

    @Override
    public void addColumn(FieldDefinition fieldDefinition, String dataType) {

        if(fieldDefinition.getId() == null) {
            throw new IllegalArgumentException("field has no id");
        }

        if(fieldDefinition.getEntityDefinitionId() == null) {
            throw new IllegalArgumentException("field has no entity id");
        }

        String tableName = tableName(fieldDefinition.getEntityDefinitionId());
        String fieldName = fieldName(fieldDefinition.getId());

        jdbcTemplate.execute(
                "ALTER TABLE " + tableName + " ADD COLUMN " + fieldName + " " + dataType
        );
    }

    private String fieldName(Long fieldDefinitionId) {
        return FIELD_PREFIX + fieldDefinitionId;
    }

    private String tableName(Long entityDefinitionId) {
        return TABLE_PREFIX + entityDefinitionId;
    }
}
