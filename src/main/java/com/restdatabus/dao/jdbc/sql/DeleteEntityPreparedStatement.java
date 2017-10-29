package com.restdatabus.dao.jdbc.sql;

import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.ID_FIELD;
import static com.restdatabus.dao.jdbc.sql.SqlConstants.tableName;

public class DeleteEntityPreparedStatement extends AbstractEntityPreparedStatement {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteEntityPreparedStatement.class);

    private Long entityId;

    public DeleteEntityPreparedStatement(EntityDefinition entityDefinition, Long id) {
        super(entityDefinition);
        this.entityId = id;
    }

    public String buildSqlTemplate() {

        LOG.debug("> buildSqlTemplate: {}", getEntityDefinition());

        boolean includeId = true;

        StringBuilder part1 = new StringBuilder("DELETE FROM " + tableName(getEntityDefinition().getId()));
        part1.append(" WHERE " + ID_FIELD + "=?");

        String sql = part1.toString();

        LOG.debug("< buildSqlTemplate: {}", sql);

        return sql;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

        PreparedStatement ps = con.prepareStatement(buildSqlTemplate(), Statement.RETURN_GENERATED_KEYS);

        LOG.debug("delete - setting statement parameter: {}", entityId);
        ps.setLong(1, entityId);

        return ps;
    }
}
