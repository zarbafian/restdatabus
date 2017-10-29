package com.restdatabus.dao.jdbc.sql;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.*;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.ID_FIELD;

public class UpdateEntityPreparedStatement extends AbstractEntityPreparedStatement {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateEntityPreparedStatement.class);

    private Entity entity;

    public UpdateEntityPreparedStatement(EntityDefinition entityDefinition, Entity entity) {
        super(entityDefinition);
        this.entity = entity;
    }

    public String buildSqlTemplate() {

        LOG.debug("> buildSqlTemplate: {}", getEntityDefinition());

        StringBuilder part1 = new StringBuilder("UPDATE " + tableName(getEntityDefinition().getId()) + " SET ");
        part1.append(buildUpdateFields(entity));
        part1.append(" WHERE id=?");

        String sql = part1.toString();

        LOG.debug("< buildSqlTemplate: {}", sql);

        return sql;
    }

    private String buildUpdateFields(Entity entity) {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<entity.getFields().size(); i++) {

            sb.append(fieldName(entity.getFields().get(i).getId()) + "=?");

            if (i < (entity.getFields().size() - 1)) {

                sb.append(", ");
            }
        }

        return sb.toString();
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

        PreparedStatement ps = con.prepareStatement(buildSqlTemplate(), new String[]{ID_FIELD});
        for (int i = 0; i < entity.getFields().size(); i++) {

            entity.getFields().get(i).setQueryParameter(ps, i + 1);
        }

        ps.setLong(entity.getFields().size() + 1, entity.getId());

        return ps;
    }
}
