package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DecimalField extends Field<Double> {

    public DecimalField() {
        super(0.);
        this.setDataType(DataType.DECIMAL);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setDouble(index, this.getValue());
    }

    @Override
    public Double readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getDouble(index);
    }

    @Override
    public Field emptyClone() {
        return new DecimalField();
    }
}
