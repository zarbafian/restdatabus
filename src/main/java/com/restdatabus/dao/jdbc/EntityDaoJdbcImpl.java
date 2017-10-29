package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EntityDao;
import com.restdatabus.dao.jdbc.sql.*;
import com.restdatabus.model.data.Entity;
import com.restdatabus.model.data.Field;
import com.restdatabus.model.data.types.FieldFactory;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldType;
import com.restdatabus.model.service.FieldTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.tableName;

@Service
public class EntityDaoJdbcImpl implements EntityDao {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDaoJdbcImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private FieldTypeService fieldTypeService;

    @Autowired
    public EntityDaoJdbcImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Entity insert(EntityDefinition entityDefinition, Entity entity) {

        LOG.debug("> insert: {}", entity);

        // Holds the id generated by database
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new InsertEntityPreparedStatement(entityDefinition, entity),
                keyHolder
        );

        entity.setId(keyHolder.getKey().longValue());

        LOG.debug("< insert: {}", entity);

        return  entity;
    }

    @Override
    public List<Entity> findByDefinition(EntityDefinition entityDefinition) {

        LOG.debug("> findByDefinition: {}", entityDefinition);

        SelectEntityPreparedStatement selectEntityPreparedStatement = new SelectEntityPreparedStatement(entityDefinition);

        List<Entity> results = jdbcTemplate.query(
                selectEntityPreparedStatement,
                new EntityRowMapper(fieldTypeService, selectEntityPreparedStatement)
        );

        LOG.debug("= findByDefinition: found {} result(s)", results.size());

        return results;
    }

    @Override
    public Entity findByDefinitionAndId(EntityDefinition entityDefinition, Long id) {

        LOG.debug("> findByDefinitionAndId: {}, {}", entityDefinition, id);

        SelectEntityPreparedStatement selectEntityPreparedStatement = new SelectEntityPreparedStatement(entityDefinition, id);

        List<Entity> results = jdbcTemplate.query(
                selectEntityPreparedStatement,
                new EntityRowMapper(fieldTypeService, selectEntityPreparedStatement)
        );

        LOG.debug("= findByDefinitionAndId: found {} result(s)", results.size());

        if(results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    @Override
    public void deleteByDefinitionAndId(EntityDefinition entityDefinition, Long id) {

        LOG.debug("> deleteByDefinitionAndId: {}, {}", entityDefinition, id);

        jdbcTemplate.update(
          new DeleteEntityPreparedStatement(entityDefinition, id)
        );
    }

    @Override
    public Entity update(EntityDefinition entityDefinition, Long id, Entity entity) {

        LOG.debug("> update: {}.{} -> ", entityDefinition.getName(), id, entity);

        UpdateEntityPreparedStatement updateEntityPreparedStatement = new UpdateEntityPreparedStatement(entityDefinition, entity);

        int result = jdbcTemplate.update(
                updateEntityPreparedStatement
        );
        LOG.debug("> update result: {}", result);

        return entity;
    }

    private static class EntityRowMapper implements RowMapper<Entity> {

        private AbstractEntityPreparedStatement abstractEntityPreparedStatement;

        private FieldTypeService fieldTypeService;

        private List<Field> fieldsTpl;

        public EntityRowMapper(FieldTypeService fieldTypeService, AbstractEntityPreparedStatement entityDefinition) {

            this.fieldTypeService = fieldTypeService;
            this.abstractEntityPreparedStatement = entityDefinition;
            this.fieldsTpl = new ArrayList<>();

            List<FieldDefinition> defs = abstractEntityPreparedStatement.getEntityDefinition().getDefinitions();

            for (int i = 0; i < defs.size(); i++) {

                FieldType fieldType = fieldTypeService.findById(defs.get(i).getFieldTypeId());
                Field field = FieldFactory.build(fieldType);
                this.fieldsTpl.add(field);
            }
        }

        @Override
        public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long id = rs.getLong(1);

            List<Field> fields = new ArrayList<>();

            List<FieldDefinition> defs = abstractEntityPreparedStatement.getEntityDefinition().getDefinitions();

            int index = 2;

            for (int i = 0; i < defs.size(); i++) {

                Field field = fieldsTpl.get(i).emptyClone();

                field.setId(defs.get(i).getId());

                Object value = field.readQueryResult(rs, index);
                field.setValue(value);

                fields.add(field);

                index++;
            }

            Map<Long, Object> values = null;

            Entity entity = new Entity();
            entity.setId(id);
            entity.setDefinitionId(abstractEntityPreparedStatement.getEntityDefinition().getId());
            entity.setFields(fields);

            return entity;
        }
    }
}
