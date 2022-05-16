package net.devk.his.command.patients;

import java.time.Instant;
import java.util.List;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

public interface PatientService {

  public String createPatient(Patient thePatient);

  public void killPatient(String patientId, Instant deceasedDate);

  public List<Patient> findPatientsByName(StringType theFamilyName);

  public Patient readPatient(IdType theId);

  public String addObservation(Observation observation);

}
