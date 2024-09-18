package bobr.navigatorMicroservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")  // Разрешить запросы от всех доменов
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Разрешить все методы HTTP
                        .allowedHeaders("*")  // Разрешить все заголовки
                        .exposedHeaders("Authorization")  // Доступ к заголовкам
                        .allowCredentials(false);  // Отключить передачу учетных данных (если не нужно)
            }
        };
    }
}
