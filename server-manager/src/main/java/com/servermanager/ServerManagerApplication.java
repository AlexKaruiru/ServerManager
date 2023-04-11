package com.servermanager;

import com.servermanager.enumeration.Status;
import com.servermanager.model.Server;
import com.servermanager.repo.ServerRepo;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication


public class ServerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagerApplication.class, args);
	}
        
        @Bean
        CommandLineRunner run(ServerRepo serverRepo){
         return args ->{
             serverRepo.save(new Server(null, "192.168.1.36", "Ubuntu Linux", "16 GB", "Personal PC", 
                     "http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
             
             serverRepo.save(new Server(null, "192.168.1.23", "Manjaro Linux", "16 GB", "Work PC", 
                     "http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN));
             
              serverRepo.save(new Server(null, "192.168.1.30", "Windows 10", "8 GB", "Game PC", 
                     "http://localhost:8080/server/image/server3.png", Status.SERVER_DOWN));
              
              serverRepo.save(new Server(null, "197.232.157.100 ", "Unknown OS", "20 GB", "Anonymous PC", 
                     "http://localhost:8080/server/image/server4.png", Status.SERVER_UP));
              
              serverRepo.save(new Server(null, "192.168.1.22 ", "Wednesday", "8 GB", "Wed PC", 
                     "http://localhost:8080/server/image/server4.png", Status.SERVER_UP));
              
              serverRepo.save(new Server(null, "192.168.1.29 ", "Windows 10", "8 GB", "Claris PC", 
                     "http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN));
              
              
         }; 
        }
        
        @Bean
	public CorsFilter corsFilter() {
                UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
