package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import the modelMapper class manually
import org.modelmapper.ModelMapper;

@Configuration
@OpenAPIDefinition(info = @Info(title = "TODO IT API", version = "0.1", description = "API Information"))
public class AppConfig {
    //step 1 -> add @Configuration in front of the class
    //step 1 -> add @OpenAPIDefinition in front of the class2
    //@OpenAPIDefinition(info = @Info(title = "TODO IT API", version = "0.1", description = "API Information"))


    //http://localhost:8080/swagger-ui/index.html - here we find automatically created documentation


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
