package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityField extends Field<Long> {

    public EntityField() {
        super();
        this.setDataType(DataType.ENTITY);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setLong(index, this.getValue());
    }

    @Override
    public Long readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getLong(index);
    }

    @Override
    public Field emptyClone() {
        return new EntityField();
    }
}
