package com.restdatabus;

import com.restdatabus.model.data.Entity;
import com.restdatabus.model.data.EntityBuilder;
import com.restdatabus.model.data.types.TextDataType;
import com.restdatabus.model.data.types.YesNoDataType;
import com.restdatabus.model.meta.EntityDefinition;
import com.restdatabus.model.meta.TextFieldDefinition;
import com.restdatabus.model.meta.YesNoFieldDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String field1 = "ff is it true?";
        String field2 = "fff aht my name";

        EntityDefinition entityDefinition = new EntityDefinition("myenttype");
        entityDefinition.setId(123L);
        entityDefinition.getDefinitions().add(new YesNoFieldDefinition(field1));
        entityDefinition.getDefinitions().add(new TextFieldDefinition(field2));

        System.out.println( "DEF: " + entityDefinition);


        EntityBuilder entityBuilder = new EntityBuilder(entityDefinition);
        entityBuilder.set(field1, true);
        entityBuilder.set(field2, "toto");

        //Entity entity = entityBuilder.create();

        //System.out.println( "ENT: " + entity);
        /*
        EntityManager manager = new EntityManager(new EntityService());

        Entity createdEntity = manager.create(entity);
*/
    }
}
