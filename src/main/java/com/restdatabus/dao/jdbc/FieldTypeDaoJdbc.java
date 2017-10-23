package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.FieldTypeDao;
import com.restdatabus.model.meta.FieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FieldTypeDaoJdbc implements FieldTypeDao {

    private static final Logger LOG = LoggerFactory.getLogger(FieldTypeDaoJdbc.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FieldTypeDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public FieldType findById(Long id) {

        LOG.debug("> findById: {}", id);

        List<FieldType> results = jdbcTemplate.query(

                "SELECT ft.id, ft.key FROM field_type ft WHERE ft.id=?",
                new Object[]{ id },
                new FieldTypeRowMapper()
        );


        LOG.debug("= findById - found: {}", results);

        if(results.isEmpty()) {
            return null;
        }

        FieldType loadedEntity = results.get(0);

        return loadedEntity;
    }

    @Override
    public FieldType findByKey(String key) {

        LOG.debug("> findByKey: {}", key);

        List<FieldType> results = jdbcTemplate.query(

                "SELECT ft.id, ft.key FROM field_type ft WHERE ft.key=?",
                new Object[]{ key },
                new FieldTypeRowMapper()
        );


        LOG.debug("= findByKey - found: {}", results);

        if(results.isEmpty()) {
            return null;
        }

        FieldType loadedEntity = results.get(0);

        return loadedEntity;
    }

    @Override
    public List<FieldType> findAll() {

        LOG.debug("> findAll");

        List<FieldType> results = jdbcTemplate.query(

                "SELECT ft.id, ft.key FROM field_type ft",
                new Object[]{},
                new FieldTypeRowMapper()
        );

        LOG.debug("< findAll: {} results", results.size());

        return results;
    }

    protected static class FieldTypeRowMapper implements RowMapper<FieldType> {

        @Override
        public FieldType mapRow(ResultSet resultSet, int i) throws SQLException {

            Long id = resultSet.getLong(1);
            String key = resultSet.getString(2);
            FieldType fieldType = new FieldType(id, key);

            return fieldType;
        }
    }
}
