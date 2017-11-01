package com.restdatabus.model.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Field<T> {

    protected Long id;

    protected DataType dataType;

    protected T value;

    public Field() {
    }

    public Field(T value) {
        this.value = value;
    }

    public abstract void setQueryParameter(PreparedStatement preparedStatement, int index) throws SQLException;

    public abstract T readQueryResult(ResultSet rs, int index) throws SQLException;

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", dataType=" + dataType +
                ", value=" + value +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract Field emptyClone();
}
