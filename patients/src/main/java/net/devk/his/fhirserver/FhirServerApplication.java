package net.devk.his.fhirserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FhirServerApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<FhirRestfulServlet> fhirRestfulServlet() {
		ServletRegistrationBean<FhirRestfulServlet> bean = new ServletRegistrationBean<>(new FhirRestfulServlet(),
				"/fhir/*");
		bean.setLoadOnStartup(1);
		return bean;
	}

}
