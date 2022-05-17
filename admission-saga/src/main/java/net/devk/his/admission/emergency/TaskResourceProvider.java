package net.devk.his.admission.emergency;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Task;
import org.springframework.stereotype.Component;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import lombok.RequiredArgsConstructor;
import net.devk.fhir.api.CreateNewAdmissionCommand;
import net.devk.fhir.api.EndEmergencyServiceCommand;

@Component
@RequiredArgsConstructor
public class TaskResourceProvider implements IResourceProvider {

  private final CommandGateway commandGateway;

  @Operation(name = "$startAdmission", idempotent = true)
  public Bundle startAdmission(@OperationParam(name = "patient") Patient patient) {
    String admissionId = UUID.randomUUID().toString();
    commandGateway.send(new CreateNewAdmissionCommand(admissionId,
        patient.getNameFirstRep().getNameAsSingleString()));
    Bundle bundle = new Bundle();
    bundle.addEntry().setId(admissionId);
    return bundle;
  }

  @Operation(name = "$endAdmission", idempotent = true)
  public MethodOutcome endAdmission(@OperationParam(name = "admissionId") StringParam admissionId) {
    commandGateway.send(new EndEmergencyServiceCommand(admissionId.getValue()));
    return new MethodOutcome();
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Task.class;
  }
}
