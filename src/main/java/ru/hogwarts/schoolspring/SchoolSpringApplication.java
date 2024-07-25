package ru.hogwarts.schoolspring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SchoolSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolSpringApplication.class, args);
	}

}
