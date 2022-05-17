package net.devk.his.command.patients;

import java.time.Instant;
import org.hl7.fhir.r4.model.Patient;

public interface PatientService {

  public String createPatient(Patient thePatient);

  public void expirePatient(String patientId, Instant deceasedDate);

}
