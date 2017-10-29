package com.restdatabus.dao.jdbc.sql;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.*;

import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.ID_FIELD;

public class SelectEntityPreparedStatement extends AbstractEntityPreparedStatement {

    private static final Logger LOG = LoggerFactory.getLogger(SelectEntityPreparedStatement.class);

    private boolean findyById;
    private Long entityId;

    public SelectEntityPreparedStatement(EntityDefinition entityDefinition) {
        super(entityDefinition);
        this.findyById = false;
    }

    public SelectEntityPreparedStatement(EntityDefinition entityDefinition, Long id) {
        super(entityDefinition);
        this.findyById = true;
        this.entityId = id;
    }

    public String buildSqlTemplate() {

        LOG.debug("> buildSqlTemplate: {}", getEntityDefinition());

        boolean includeId = true;

        StringBuilder part1 = new StringBuilder("SELECT ");
        part1.append(getFieldsList(includeId));
        part1.append(" FROM " + tableName(getEntityDefinition().getId()));

        if ((findyById)) {
            part1.append(" WHERE " + ID_FIELD + "=?");
        }

        String sql = part1.toString();

        LOG.debug("< buildSqlTemplate: {}", sql);

        return sql;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

        PreparedStatement ps = con.prepareStatement(buildSqlTemplate(), Statement.RETURN_GENERATED_KEYS);

        if(findyById) {
            LOG.debug("select - setting statement parameter: {}", entityId);
            ps.setLong(1, entityId);
        }
        return ps;
    }
}
