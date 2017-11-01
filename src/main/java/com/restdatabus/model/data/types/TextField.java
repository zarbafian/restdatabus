package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TextField extends Field<String> {

    public TextField() {
        super("");
        this.setDataType(DataType.TEXT);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setString(index, this.getValue());
    }

    @Override
    public String readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getString(index);
    }

    @Override
    public Field emptyClone() {
        return new TextField();
    }
}
