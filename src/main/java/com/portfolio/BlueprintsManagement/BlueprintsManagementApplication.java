package com.portfolio.BlueprintsManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "図面管理システム"))
@SpringBootApplication
public class BlueprintsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueprintsManagementApplication.class, args);
    }

}
