package com.restdatabus;

import static org.assertj.core.api.Assertions.*;

import static com.restdatabus.web.api.Constants.*;

public class DefinitionsUrlTest {

    @org.junit.Test
    public void apiConstants() {

        assertThat(DEFINITIONS.equals("/api/definitions")).isTrue();
        assertThat(DEFINITION_BY_NAME.equals("/api/definitions/{name}")).isTrue();
        assertThat(FIELDS_BY_NAME.equals("/api/definitions/{name}/fields")).isTrue();
        assertThat(FIELD_BY_NAME_AND_FIELD.equals("/api/definitions/{name}/fields/{field}")).isTrue();
    }
}
