package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.FieldDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import com.restdatabus.model.meta.FieldDefinitionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * DaoJdbc layer: perform database interactions
 */
@Service
public class FieldDefinitionDaoJdbc implements FieldDefinitionDao {

    private static final Logger LOG = LoggerFactory.getLogger(FieldDefinitionDaoJdbc.class);

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertFieldDefinition;

    @Autowired
    public FieldDefinitionDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertFieldDefinition = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("field_definition")
                                        .usingColumns("name", "type", "entity_definition_id")
                                        .usingGeneratedKeyColumns("id");
    }

    public FieldDefinition insert(FieldDefinition field) {

        LOG.debug("> insert: {}", field);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", field.getName());
        parameters.put("type", field.getType().getKey());
        parameters.put("entity_definition_id", field.getEntityDefinitionId());
        Number newId = insertFieldDefinition.executeAndReturnKey(parameters);

        FieldDefinition persistedField = new FieldDefinition(field);
        persistedField.setId(newId.longValue());

        LOG.debug("< insert: {}", persistedField);

        return persistedField;
    }

    public List<FieldDefinition> findByEntityDefinition(EntityDefinition entityDefinition) {

        LOG.debug("> findByEntityDefinition: {}", entityDefinition);

        List<FieldDefinition> results = jdbcTemplate.query(

                "SELECT fd.id, fd.name, fd.type, fd.entity_definition_id FROM field_definition fd WHERE fd.entity_definition_id=?",
                new Object[]{ entityDefinition.getId() },
                new FieldDefinitionRowMapper()
        );

        LOG.debug("< findByEntityDefinition: found {} field definitions(s)", results.size());

        return results;
    }

    @Override
    public void delete(Long id) {

        LOG.debug("> delete: {}", id);

        jdbcTemplate.update(
                "DELETE FROM field_definition WHERE id=?",
                new Object[] { id }
        );

        LOG.debug("< delete: {}", id);
    }

    @Override
    public void deleteByEntityDefinition(Long id) {

        LOG.debug("> deleteByEntityDefinition: {}", id);

        jdbcTemplate.update(
                "DELETE FROM field_definition WHERE entity_definition_id=?",
                new Object[] { id }
        );

        LOG.debug("< deleteByEntityDefinition: {}", id);
    }

    @Override
    public FieldDefinition update(FieldDefinition fieldDefinition) {

        LOG.debug("> update: {}", fieldDefinition);

        jdbcTemplate.update(
                "UPDATE field_definition SET name=?, type=? WHERE id=?",
                new Object[] { fieldDefinition.getName(), fieldDefinition.getType().getKey(), fieldDefinition.getId() }
        );

        LOG.debug("< update: {}", fieldDefinition);

        return fieldDefinition;
    }

    @Override
    public FieldDefinition findByDefinitionAndName(Long entityDefinitionId, String fieldName) {

        LOG.debug("> findByDefinitionAndName: {} -> {}", entityDefinitionId, fieldName);

        List<FieldDefinition> results = jdbcTemplate.query(

                "SELECT fd.id, fd.name, fd.type, fd.entity_definition_id FROM field_definition fd WHERE fd.entity_definition_id=? AND fd.name=?",
                new Object[]{ entityDefinitionId, fieldName },
                new FieldDefinitionRowMapper()
        );

        LOG.debug("< findByDefinitionAndName: found {} field definitions(s)", results.size());

        if(results.size() == 0) {
            return null;
        }

        return results.get(0);
    }

    protected static class FieldDefinitionRowMapper implements RowMapper<FieldDefinition> {

        @Override
        public FieldDefinition mapRow(ResultSet resultSet, int i) throws SQLException {

            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String type = resultSet.getString(3);
            Long entityDefId = resultSet.getLong(4);

            FieldDefinition fieldDefinition = FieldDefinitionFactory.buildFromKey(type, name);
            fieldDefinition.setId(id);
            fieldDefinition.setEntityDefinitionId(entityDefId);

            return fieldDefinition;
        }
    }
}
