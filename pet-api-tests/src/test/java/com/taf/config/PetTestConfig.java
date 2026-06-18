package com.taf.config;

import com.taf.restapi.TafRestApiConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TafRestApiConfiguration.class)
public class PetTestConfig {
}
