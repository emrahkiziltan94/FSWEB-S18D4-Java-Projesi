package com.workintech.s18d4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@WebMvcTest(ApplicationPropertiesKeyTests.class)
class ApplicationPropertiesKeyTests {

    @Autowired
    private Environment env;

    @Test
    @DisplayName("application properties istenilenler eklendi mi?")
    void serverPortIsSetTo8585() {

        String serverPort = env.getProperty("server.port");
        assertThat(serverPort).isEqualTo("8080");



        String datasourceUrl = env.getProperty("spring.datasource.url");
        assertNotNull(datasourceUrl);

        String datasourceUsername = env.getProperty("spring.datasource.username");
        assertNotNull(datasourceUsername);

        String datasourcePassword = env.getProperty("spring.datasource.password");
        assertNotNull(datasourcePassword);

        String hibernateDdlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        assertNotNull(hibernateDdlAuto);


    }
}


