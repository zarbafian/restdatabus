package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YesNoField extends Field<Boolean> {

    public YesNoField() {
        super();
        this.setDataType(DataType.YESNO);
    }

    @Override
    public void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException {
        preparedStatement.setBoolean(index, this.getValue());
    }

    @Override
    public Boolean readQueryResult(ResultSet rs, int index) throws SQLException {
        return rs.getBoolean(index);
    }

    @Override
    public Field emptyClone() {
        return new YesNoField();
    }
}
