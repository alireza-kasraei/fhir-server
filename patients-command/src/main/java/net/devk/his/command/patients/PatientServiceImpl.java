package net.devk.his.command.patients;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.stereotype.Service;
import net.devk.fhir.api.CreateNewPatientCommand;
import net.devk.fhir.api.KillPatientCommand;

@Service
class PatientServiceImpl implements PatientService {


  private final CommandGateway commandGateway;
  // private final QueryGateway queryGateway;



  public PatientServiceImpl(CommandGateway commandGateway) {
    super();
    this.commandGateway = commandGateway;
    // this.queryGateway = queryGateway;
  }

  @Override
  public String createPatient(Patient thePatient) {
    String patientId = UUID.randomUUID().toString();
    commandGateway.sendAndWait(new CreateNewPatientCommand(patientId,
        thePatient.getNameFirstRep().getNameAsSingleString()));
    return patientId;
  }

  @Override
  public void killPatient(String patientId, Instant deceasedDate) {
    commandGateway.sendAndWait(new KillPatientCommand(patientId, deceasedDate));
  }

  @Override
  public List<Patient> findPatientsByName(StringType theFamilyName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Patient readPatient(IdType theId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String addObservation(Observation observation) {
    // TODO Auto-generated method stub
    return null;
  }



}
