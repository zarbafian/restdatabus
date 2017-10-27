package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EntityDefinitionDao;
import com.restdatabus.dao.FieldDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityDefinitionDaoJdbc implements EntityDefinitionDao {

    private static final Logger LOG = LoggerFactory.getLogger(EntityDefinitionDaoJdbc.class);

    @Autowired
    private FieldDefinitionDao fieldDefinitionDao;

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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", entity.getName());
        Number newId = insertEntityDefinition.executeAndReturnKey(parameters);

        EntityDefinition persistedEntity = new EntityDefinition(entity.getName());
        persistedEntity.setId(newId.longValue());

        LOG.debug("< insert: {}", persistedEntity);

        return persistedEntity;
    }

    @Override
    public EntityDefinition findById(Long id) {

        LOG.debug("> findById: {}", id);

        return findByUniqueKey(

                "SELECT ed.id, ed.name FROM entity_definition ed WHERE ed.id=?",
                new Object[]{id}
        );
    }

    @Override
    public EntityDefinition findByName(String name) {

        LOG.debug("> findByName: {}", name);

        return findByUniqueKey(

                "SELECT ed.id, ed.name FROM entity_definition ed WHERE ed.name=?",
                new Object[]{name}
        );
    }

    public <T> EntityDefinition findByUniqueKey(String sql, Object[] params) {

        LOG.debug("> findByUniqueKey: {} -> ", sql, params);

        List<EntityDefinition> results = jdbcTemplate.query(

                sql,
                params,
                new EntityDefinitionRowMapper()
        );


        LOG.debug("= findByUniqueKey - found: {}", results);

        if(results.isEmpty()) {
            return null;
        }

        EntityDefinition loadedEntity = results.get(0);

        List<FieldDefinition> fieldDefinitions = fieldDefinitionDao.findByEntityDefinition(loadedEntity);

        LOG.debug("= findByUniqueKey - fields: {}", fieldDefinitions);

        loadedEntity.setDefinitions(fieldDefinitions);

        return loadedEntity;
    }

    @Override
    public List<EntityDefinition> findAll() {

        LOG.debug("> findAll");

        List<EntityDefinition> results = jdbcTemplate.query(

                "SELECT ed.id, ed.name FROM entity_definition ed",
                new Object[]{},
                new EntityDefinitionRowMapper()
        );

        LOG.debug("< findAll: {} results", results.size());

        return results;
    }

    @Override
    public void delete(Long id) {

        LOG.debug("> delete");

        jdbcTemplate.update(
                "DELETE FROM entity_definition WHERE id=?",
                id
        );

        LOG.debug("< delete");
    }

    @Override
    public EntityDefinition update(EntityDefinition entityDefinition) {

        LOG.debug("> update: {}", entityDefinition);

        jdbcTemplate.update(
                "UPDATE entity_definition SET name=? WHERE id=?",
                entityDefinition.getName(), entityDefinition.getId()
        );

        LOG.debug("< update: {}", entityDefinition);

        return entityDefinition;
    }

    protected static class EntityDefinitionRowMapper implements RowMapper<EntityDefinition> {

        @Override
        public EntityDefinition mapRow(ResultSet resultSet, int i) throws SQLException {

            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            EntityDefinition entityDefinition = new EntityDefinition(name);
            entityDefinition.setId(id);

            return entityDefinition;
        }
    }
}
