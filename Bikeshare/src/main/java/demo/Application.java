package demo;

import interceptors.SessionValidatorInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import controller.BikeShareController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application{

    public static void main(String[] args) {
    	SpringApplication.run(BikeShareController.class, args);
    }
    
}
