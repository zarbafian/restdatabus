package com.restdatabus.dao.jdbc.sql;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.*;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.meta.EntityDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEntityPreparedStatement extends AbstractEntityPreparedStatement {

    private static final Logger LOG = LoggerFactory.getLogger(InsertEntityPreparedStatement.class);

    private Entity entity;

    public InsertEntityPreparedStatement(EntityDefinition entityDefinition, Entity entity) {
        super(entityDefinition);
        this.entity = entity;
    }

    public String buildSqlTemplate() {

        LOG.debug("> buildSqlTemplate: {}", getEntityDefinition());

        boolean includeId = false;

        StringBuilder part1 = new StringBuilder();

        if(entity.getFields().isEmpty()) {

            part1.append("INSERT INTO " + tableName(getEntityDefinition().getId()) + " DEFAULT VALUES");
        }
        else {
            part1.append("INSERT INTO " + tableName(getEntityDefinition().getId()));
            part1.append("(");
            part1.append(buildInsertFields(entity));
            part1.append(") VALUES(");
            part1.append(buildValuesPart(entity));
            part1.append(")");
        }

        String sql = part1.toString();

        LOG.debug("< buildSqlTemplate: {}", sql);

        return sql;
    }

    private String buildInsertFields(Entity entity) {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<entity.getFields().size(); i++) {

            sb.append(fieldName(entity.getFields().get(i).getId()));

            if (i < (entity.getFields().size() - 1)) {

                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private String buildValuesPart(Entity entity) {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<entity.getFields().size(); i++) {

            sb.append("?");

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

            int index = getEntityDefinition().getParameterIndex(entity.getFields().get(i).getId());
            entity.getFields().get(i).setQueryParameter(ps, index);
        }

        return ps;
    }
}
