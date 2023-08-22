package com.example.uruG;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UruG620Application {

	public static void main(String[] args) {
		SpringApplication.run(UruG620Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("Content-Type")
						.exposedHeaders("Content-Type")
						.allowCredentials(false)
						.maxAge(3600);
			}
		};
	}
}
