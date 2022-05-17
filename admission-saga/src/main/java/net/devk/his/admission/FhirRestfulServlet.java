package net.devk.his.admission;

import java.util.Map;
import org.springframework.context.ApplicationContext;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;

public class FhirRestfulServlet extends RestfulServer {

  private static final long serialVersionUID = 1L;
  private final ApplicationContext applicationContext;

  public FhirRestfulServlet(ApplicationContext applicationContext) {
    super(FhirContext.forR4());
    this.applicationContext = applicationContext;
  }

  @Override
  public void initialize() {
    Map<String, IResourceProvider> beansOfType =
        applicationContext.getBeansOfType(IResourceProvider.class);
    setResourceProviders(beansOfType.values());
  }

}
