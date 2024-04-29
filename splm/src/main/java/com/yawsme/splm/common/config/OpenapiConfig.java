package com.yawsme.splm.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Splm Backend",
        version = "0.0.1",
        contact = @Contact(
            name = "zomco", email = "zomco@sina.com", url = "https://github.com/zomco"
        ),
        license = @License(
            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),
        termsOfService = "terms of service",
        description = "description"
    ),
    servers = @Server(
        url = "127.0.0.1:8080",
        description = "Production"
    )
)
public class OpenapiConfig {
}
