package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.FieldDefinitionDao;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;

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

        FieldDefinition persistedField = field.clone();
        persistedField.setId(newId.longValue());

        LOG.debug("< insert: {}", persistedField);

        return persistedField;
    }
}
