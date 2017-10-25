package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.RelationDefinitionDao;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.RelationDefinition;
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

/**
 * DaoJdbc layer: perform database interactions
 */
@Service
public class RelationDefinitionDaoJdbc implements RelationDefinitionDao {

    private static final Logger LOG = LoggerFactory.getLogger(RelationDefinitionDaoJdbc.class);

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertRelationDefinition;

    @Autowired
    public RelationDefinitionDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertRelationDefinition = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("relation_definition")
                                        .usingColumns("field_definition_id", "entity_definition_id")
                                        .usingGeneratedKeyColumns("id");
    }

    public RelationDefinition insert(RelationDefinition relationDefinition) {

        LOG.debug("> insert: {}", relationDefinition);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("field_definition_id", relationDefinition.getOriginField());
        parameters.put("entity_definition_id", relationDefinition.getTargetEntity());
        Number newId = insertRelationDefinition.executeAndReturnKey(parameters);

        RelationDefinition persistedRelationDefinition = new RelationDefinition(relationDefinition);
        persistedRelationDefinition.setId(newId.longValue());

        LOG.debug("< insert: {}", persistedRelationDefinition);

        return persistedRelationDefinition;
    }

    public List<RelationDefinition> findByEntityDefinition(EntityDefinition entityDefinition) {

        LOG.debug("> findByEntityDefinition: {}", entityDefinition);

        List<RelationDefinition> results = jdbcTemplate.query(

                "SELECT rd.id, rd.field_definition_id, rd.entity_definition_id FROM relation_definition rd WHERE rd.entity_definition_id=?",
                new Object[]{ entityDefinition.getId() },
                new RelationDefinitionRowMapper()
        );

        LOG.debug("< findByEntityDefinition: found {} field definitions(s)", results.size());

        return results;
    }

    @Override
    public void delete(Long id) {

        LOG.debug("> delete: {}", id);

        jdbcTemplate.update(
                "DELETE FROM relation_definition WHERE id=?",
                id
        );

        LOG.debug("< delete: {}", id);
    }

    @Override
    public void deleteByFieldId(Long fieldDefId) {

        LOG.debug("> deleteByFieldId: {}", fieldDefId);

        jdbcTemplate.update(
                "DELETE FROM relation_definition WHERE field_definition_id=?",
                fieldDefId
        );

        LOG.debug("< deleteByFieldId: {}", fieldDefId);
    }

    @Override
    public RelationDefinition update(RelationDefinition relationDefinition) {

        LOG.debug("> update: {}", relationDefinition);

        jdbcTemplate.update(
                "UPDATE relation_definition SET entity_definition_id=? WHERE id=?",
                relationDefinition.getTargetEntity(), relationDefinition.getId()
        );

        LOG.debug("< update: {}", relationDefinition);

        return relationDefinition;
    }

    @Override
    public RelationDefinition findBySourceAndTarget(Long fieldDefinitionId, Long entityDefinitionId) {

        LOG.debug("> findBySourceAndTarget: {} -> {}", fieldDefinitionId, entityDefinitionId);

        List<RelationDefinition> results = jdbcTemplate.query(

                "SELECT rd.id, rd.field_definition_id, rd.entity_definition_id FROM relation_definition rd WHERE rd.field_definition_id=? AND rd.entity_definition_id=?",
                new Object[]{ fieldDefinitionId, entityDefinitionId },
                new RelationDefinitionRowMapper()
        );

        LOG.debug("< findBySourceAndTarget: found {} relation definitions(s)", results.size());

        if(results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    protected static class RelationDefinitionRowMapper implements RowMapper<RelationDefinition> {

        @Override
        public RelationDefinition mapRow(ResultSet resultSet, int i) throws SQLException {

            Long id = resultSet.getLong(1);
            Long fieldDefId = resultSet.getLong(2);
            Long entityDefId = resultSet.getLong(3);

            RelationDefinition relationDefinition = new RelationDefinition(id, fieldDefId, entityDefId);

            return relationDefinition;
        }
    }
}
