package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EntityDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EntityDefinitionDaoJdbc implements EntityDefinitionDao {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionDaoJdbc.class);

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertEntityDefinition;

    @Autowired
    public EntityDefinitionDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertEntityDefinition = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("entity_definition")
                                        .usingColumns("name")
                                        .usingGeneratedKeyColumns("id");
    }

    public EntityDefinition insert(EntityDefinition entity) {

        LOG.debug("> insert: {}", entity);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", entity.getName());
        Number newId = insertEntityDefinition.executeAndReturnKey(parameters);

        EntityDefinition persistedEntity = new EntityDefinition(entity.getName());
        persistedEntity.setId(newId.longValue());

        LOG.debug("> insert: {}", persistedEntity);

        return persistedEntity;
    }
}
