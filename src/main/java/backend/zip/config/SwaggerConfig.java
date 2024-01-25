package backend.zip.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "이사.ZIP OPEN API",description = "UMC 5th 이사.ZIP 프로젝트", version = "v1"))
@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    @Bean
    public OpenAPI openAPI(){
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme().name(securityJwtName).type(SecurityScheme.Type.HTTP).scheme(BEARER_TOKEN_PREFIX).bearerFormat(securityJwtName));
        return new OpenAPI().addSecurityItem(securityRequirement).components(components);
    }

}
