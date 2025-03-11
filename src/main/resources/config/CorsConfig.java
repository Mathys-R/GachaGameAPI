// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.bind.annotation.RequestMethod;

// @Configuration
// public class CorsConfig {
//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**") // Applique CORS à toutes les routes
//                         .allowedOrigins("http://localhost:8080") // Autorise ton frontend
//                         .allowedMethods(
//                             RequestMethod.GET.name(),
//                             RequestMethod.POST.name(),
//                             RequestMethod.PUT.name(),
//                             RequestMethod.DELETE.name(),
//                             RequestMethod.OPTIONS.name()
//                         ) // Autorise ces méthodes
//                         .allowedHeaders("*") // Autorise tous les headers
//                         .allowCredentials(true); // Autorise les cookies et authentification
//             }
//         };
//     }
// }
