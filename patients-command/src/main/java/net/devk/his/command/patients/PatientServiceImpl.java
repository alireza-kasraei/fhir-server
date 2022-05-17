package net.devk.his.command.patients;

import java.time.Instant;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;
import net.devk.fhir.api.commands.CreateNewPatientCommand;
import net.devk.fhir.api.commands.ExpirePatientCommand;

@Service
class PatientServiceImpl implements PatientService {

  private final CommandGateway commandGateway;

  public PatientServiceImpl(CommandGateway commandGateway) {
    super();
    this.commandGateway = commandGateway;
  }

  @Override
  public String createPatient(Patient thePatient) {
    String patientId = UUID.randomUUID().toString();
    commandGateway.send(new CreateNewPatientCommand(patientId,
        thePatient.getNameFirstRep().getNameAsSingleString()));
    return patientId;
  }

  @Override
  public void expirePatient(String patientId, Instant deceasedDate) {
    commandGateway.send(new ExpirePatientCommand(patientId, deceasedDate));
  }


}
