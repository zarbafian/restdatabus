package com.restdatabus;

import static org.assertj.core.api.Assertions.*;

import static com.restdatabus.web.api.Constants.*;

public class DefinitionsUrlTest {

    @org.junit.Test
    public void apiConstants() {

        assertThat(DEFINITIONS.equals("/definitions")).isTrue();
        assertThat(DEFINITION_BY_NAME.equals("/definitions/{name}")).isTrue();
        assertThat(FIELDS_BY_NAME.equals("/definitions/{name}/fields")).isTrue();
        assertThat(FIELDS_BY_NAME.equals("/definitions/{name}/fields/{field}")).isTrue();
    }
}
