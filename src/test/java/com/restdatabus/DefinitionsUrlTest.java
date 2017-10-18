package com.restdatabus;

import static org.assertj.core.api.Assertions.*;

import static com.restdatabus.web.api.Constants.*;

public class DefinitionsUrlTest {

    @org.junit.Test
    public void apiConstants() {

        assertThat(DEFINITIONS.equals("/definitions"));
        assertThat(DEFINITION_BY_NAME.equals("/definitions/{name}"));
        assertThat(FIELDS_BY_NAME.equals("/definitions/{name}/fields"));
        assertThat(FIELDS_BY_NAME.equals("/definitions/{name}/fields/{field}"));
    }
}
