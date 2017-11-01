package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DateTimeField extends Field<Date> {

    public DateTimeField() {
        super();
        this.setDataType(DataType.DATETIME);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setDate(index, this.getValue());
    }

    @Override
    public Date readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getDate(index);
    }

    @Override
    public Field emptyClone() {
        return new DateTimeField();
    }
}
