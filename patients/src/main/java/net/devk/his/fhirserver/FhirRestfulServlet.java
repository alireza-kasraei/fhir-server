package net.devk.his.fhirserver;

import java.util.Map;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import net.devk.his.fhirserver.utils.SpringContextUtil;

public class FhirRestfulServlet extends RestfulServer {

	private static final long serialVersionUID = 1L;

	public FhirRestfulServlet() {
		super(FhirContext.forR4());
	}

	@Override
	public void initialize() {
		Map<String, IResourceProvider> beansOfType = SpringContextUtil.getApplicationContext()
				.getBeansOfType(IResourceProvider.class);
		setResourceProviders(beansOfType.values());
	}

}
