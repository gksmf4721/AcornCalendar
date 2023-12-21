package acorn.calendar.config.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;

import java.util.Map;

// 현재 사용 X - 테스트용

@Configuration
public class JacksonConfig {

//    @Bean
//    public MappingJackson2CborHttpMessageConverter objectMapper() {
//        MappingJackson2CborHttpMessageConverter converter = new MappingJackson2CborHttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        converter.setObjectMapper(objectMapper);
//        return converter;
//    }
}
