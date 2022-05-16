package net.devk.his.command;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class PatientsCommandApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatientsCommandApplication.class, args);
  }

  @Bean
  public ServletRegistrationBean<FhirRestfulServlet> fhirRestfulServlet(
      ApplicationContext applicationContext) {
    ServletRegistrationBean<FhirRestfulServlet> bean =
        new ServletRegistrationBean<>(new FhirRestfulServlet(applicationContext), "/fhir/*");
    bean.setLoadOnStartup(1);
    return bean;
  }

  @Bean
  @Qualifier("messageSerializer")
  public Serializer messageSerializer() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return JacksonSerializer.builder().objectMapper(objectMapper).lenientDeserialization().build();
  }



}
