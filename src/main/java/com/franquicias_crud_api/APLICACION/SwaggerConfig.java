package com.franquicias_crud_api.APLICACION;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestión de Franquicias",
                version = "1.0.0",
                description = "API REST desarrollada con Spring Boot para gestionar franquicias, sucursales y productos. Permite operaciones de creación, actualización, consulta y eliminación.",
                contact = @Contact(
                        name = "Juan Pablo Herrera Herrera",
                        email = "animakkusuxv@gmail.com",
                        url = "https://github.com/Juanpabloxv/franquicias-crud-api"
                )
        )
)
public class SwaggerConfig {
}
