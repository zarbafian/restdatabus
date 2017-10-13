package com.restdatabus.model.meta;

import com.restdatabus.model.data.DataType;
import com.restdatabus.model.data.types.YesNoDataType;

/**
 * A field having only two possible values: yes and no.
 */
public class YesNoFieldDefinition extends FieldDefinition<YesNoDataType> {

    public YesNoFieldDefinition(String name) {
        super(name, DataType.YESNO);
    }
}
