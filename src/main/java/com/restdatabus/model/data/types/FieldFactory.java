package com.restdatabus.model.data.types;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.Field;
import com.restdatabus.model.meta.FieldType;

public class FieldFactory {

    private FieldFactory() {}

    public static Field build(FieldType fieldType) {

        DataType dataType = DataType.fromKey(fieldType.getKey());

        switch (dataType) {
            case YESNO:
                return new YesNoField();
            case INTEGER:
                return new IntegerField();
            case TEXT:
                return new TextField();
            case ENTITY:
                return new EntityField();
            case PARAGRAPH:
                return new ParagraphField();
            case DATE:
                return new DateField();
            case DATETIME:
                return new DateTimeField();
            case DECIMAL:
                return new DecimalField();
            case FILE:
                return new FileField();
            default:
                throw new UnsupportedOperationException("data type '" + dataType + "' not implemented");
        }
    }
}
