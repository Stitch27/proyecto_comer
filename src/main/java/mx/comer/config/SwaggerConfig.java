package mx.comer.config;

import java.util.Collections;
import springfox.documentation.service.ApiInfo;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket etiqueta() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("mx.comer.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(informacion());
    }

    private ApiInfo informacion() {

        return new ApiInfo(
                "Servicios de Usuarios",
                "Gestionar la información de los usuarios",
                "1.0",
                "",
                null,
                "",
                "",
                Collections.emptyList()
        );
    }

}
