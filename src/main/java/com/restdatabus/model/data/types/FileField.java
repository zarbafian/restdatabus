package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileField extends Field<byte[]> {

    public FileField() {
        super();
        this.setDataType(DataType.FILE);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setBytes(index, this.getValue());
    }

    @Override
    public byte[] readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getBytes(index);
    }

    @Override
    public Field emptyClone() {
        return new FileField();
    }
}
