package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerField extends Field<Integer> {

    public IntegerField() {
        super();
        this.setDataType(DataType.INTEGER);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setInt(index, this.getValue());
    }

    @Override
    public Integer readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getInt(index);
    }

    @Override
    public Field emptyClone() {
        return new IntegerField();
    }
}
