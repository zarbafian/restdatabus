package com.restdatabus.dao.jdbc;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.*;

import com.restdatabus.dao.EntityTableDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EntityTableDaoJdbcImpl implements EntityTableDao {

    private static final Logger LOG = LoggerFactory.getLogger(EntityTableDaoJdbcImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EntityTableDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createTable(EntityDefinition entityDefinition) {

        LOG.debug("> createTable: {}", entityDefinition);

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
    public void deleteTable(EntityDefinition entityDefinition) {

        LOG.debug("> deleteTable: {}", entityDefinition);

        if(entityDefinition.getId() == null) {
            throw new IllegalArgumentException("entity has no id");
        }

        String tableName = tableName(entityDefinition.getId());

        jdbcTemplate.execute(
                "DROP TABLE " + tableName
        );
    }

    @Override
    public void addColumn(FieldDefinition fieldDefinition, String dataType) {

        LOG.debug("> addColumn: {} - {}", fieldDefinition, dataType);

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

    @Override
    public void removeColumn(FieldDefinition fieldDefinition) {

        LOG.debug("> removeColumn: {}", fieldDefinition);

        if(fieldDefinition.getId() == null) {
            throw new IllegalArgumentException("field has no id");
        }

        if(fieldDefinition.getEntityDefinitionId() == null) {
            throw new IllegalArgumentException("field has no entity id");
        }

        String tableName = tableName(fieldDefinition.getEntityDefinitionId());
        String fieldName = fieldName(fieldDefinition.getId());

        jdbcTemplate.execute(
                "ALTER TABLE " + tableName + " DROP COLUMN " + fieldName
        );
    }

    @Override
    public void changeColumnType(FieldDefinition fieldDefinition, String newDataType) {

        LOG.debug("> changeColumnType: {} - {}", fieldDefinition, newDataType);

        if(fieldDefinition.getId() == null) {
            throw new IllegalArgumentException("field has no id");
        }

        if(fieldDefinition.getEntityDefinitionId() == null) {
            throw new IllegalArgumentException("field has no entity id");
        }

        String tableName = tableName(fieldDefinition.getEntityDefinitionId());
        String fieldName = fieldName(fieldDefinition.getId());

        jdbcTemplate.execute(
                "ALTER TABLE " + tableName + " ALTER COLUMN " + fieldName + " SET DATA TYPE " + newDataType
        );
    }

    @Override
    public void addForeignKey(FieldDefinition fieldDefinition) {

        String tableName = tableName(fieldDefinition.getEntityDefinitionId());
        String fieldName = fieldName(fieldDefinition.getId());
        String targetTable = tableName(fieldDefinition.getTargetEntityId());
        String constraintName = foreignKeyName(tableName, fieldName, targetTable);
        String indexName = foreignKeyIndexName(tableName, fieldName, targetTable);

        String sqlFk = "" +
                "ALTER TABLE " + tableName +
                "  ADD CONSTRAINT " + constraintName + " FOREIGN KEY (" + fieldName + ") REFERENCES " + targetTable + " (" + ID_FIELD + ") " +
                "  ON UPDATE NO ACTION ON DELETE NO ACTION";

        jdbcTemplate.execute(sqlFk);

        String sqlIdx = "" +
                "CREATE INDEX " + indexName + " " +
                "  ON " + tableName + " USING btree (" + fieldName + ")";

        jdbcTemplate.execute(sqlIdx);
    }

    @Override
    public void removeIndexedForeignKey(FieldDefinition fieldDefinition) {

        String tableName = tableName(fieldDefinition.getEntityDefinitionId());
        String fieldName = fieldName(fieldDefinition.getId());
        String targetTable = tableName(fieldDefinition.getTargetEntityId());
        String constraintName = foreignKeyName(tableName, fieldName, targetTable);
        String indexName = foreignKeyIndexName(tableName, fieldName, targetTable);

        String sqlFk = "" +
                "ALTER TABLE " + tableName +
                "  DROP CONSTRAINT " + constraintName;

        jdbcTemplate.execute(sqlFk);

        String sqlIdx = "" +
                "DROP INDEX " + indexName;

        jdbcTemplate.execute(sqlIdx);
    }
}
