package com.restdatabus.web.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /** Needs authentication
    @Test
    public void testAll() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + 8080 + Constants.DEFINITIONS,
                String.class)).contains("product", "invoice", "customer");
    }
    */
}
