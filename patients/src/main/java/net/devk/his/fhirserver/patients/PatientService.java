package net.devk.his.fhirserver.patients;

import java.util.List;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

import ca.uhn.fhir.rest.api.MethodOutcome;

public interface PatientService {

	public MethodOutcome createPatient(Patient thePatient);

	public List<Patient> findPatientsByName(StringType theFamilyName);

	public List<Patient> findPatientsUsingArbitraryCtriteria();

	public Patient readPatient(IdType theId);

	public MethodOutcome updatePatient(IdType theId, Patient thePatient);
}
