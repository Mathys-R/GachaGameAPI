// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.servers.Server;
// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class SwaggerConfig {

//     @Bean
//     public OpenAPI customOpenAPI() {
//         return new OpenAPI()
//             .info(new Info()
//                 .title("Gacha Game API")
//                 .version("1.0")
//                 .description("API Documentation for Gacha Game"))
//             .addServersItem(new Server().url("http://localhost:8080").description("Main Spring App API"))
//             .addServersItem(new Server().url("http://localhost:8081").description("Auth API"))
//             .addServersItem(new Server().url("http://localhost:8082").description("Player API"))
//             .addServersItem(new Server().url("http://localhost:8083").description("Monsters API"))
//             .addServersItem(new Server().url("http://localhost:8084").description("Summon API"))
//             .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//             .components(new Components().addSecuritySchemes("bearerAuth",
//                 new SecurityScheme()
//                     .type(SecurityScheme.Type.HTTP)
//                     .scheme("bearer")
//                     .bearerFormat("JWT")));
//     }
// }
