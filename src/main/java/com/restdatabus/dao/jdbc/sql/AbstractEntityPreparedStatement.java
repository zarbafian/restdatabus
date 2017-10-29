package com.restdatabus.dao.jdbc.sql;

import static com.restdatabus.dao.jdbc.sql.SqlConstants.*;

import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

public abstract class AbstractEntityPreparedStatement implements PreparedStatementCreator {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityPreparedStatement.class);

    private EntityDefinition entityDefinition;

    public AbstractEntityPreparedStatement() {
    }

    public AbstractEntityPreparedStatement(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;
    }

    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public String getFieldsList(boolean includeId) {

        LOG.debug("getFieldsList: {}", includeId);

        StringBuilder sb = new StringBuilder(
                includeId ?
                        (ID_FIELD + (
                                entityDefinition.getDefinitions().size() > 1 ?
                                        ", " :
                                        "")) :
                        ""
        );

        for (int i = 0; i < entityDefinition.getDefinitions().size(); i++) {

            FieldDefinition fd = entityDefinition.getDefinitions().get(i);

            sb.append(fieldName(fd.getId()));

            if (i < (entityDefinition.getDefinitions().size() - 1)) {

                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public String getParametersPlaceHolder(boolean includeId) {

        LOG.debug("getParametersPlaceHolder: {}", includeId);

        StringBuilder sb = new StringBuilder(
                includeId ?
                        ("?" + (entityDefinition.getDefinitions().size() > 1 ?
                                ", " :
                                "")) :
                        ""
        );

        for (int i = 0; i < entityDefinition.getDefinitions().size(); i++) {

            sb.append("?");

            if (i < (entityDefinition.getDefinitions().size() - 1)) {

                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public abstract String buildSqlTemplate();
}
