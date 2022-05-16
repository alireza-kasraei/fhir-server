package net.devk.his.command.providers;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import net.devk.his.command.patients.PatientService;

/**
 * This is a resource provider which stores Patient resources in memory using a HashMap. This is
 * obviously not a production-ready solution for many reasons, but it is useful to help illustrate
 * how to build a fully-functional server.
 */
@Component
public class PatientResourceProvider implements IResourceProvider {

  private final PatientService patientService;

  public PatientResourceProvider(PatientService patientService) {
    this.patientService = patientService;
  }

  /**
   * The "@Create" annotation indicates that this method implements "create=type", which adds a new
   * instance of a resource to the server.
   */
  @Create()
  public MethodOutcome createPatient(@ResourceParam Patient thePatient) {
    String patientId = patientService.createPatient(thePatient);
    MethodOutcome retVal = new MethodOutcome();
    retVal.setId(new IdType("Patient", patientId));
    return retVal;
  }


  @Operation(name = "$kill", idempotent = true)
  public MethodOutcome kill(@IdParam IdType thePatientId,
      @OperationParam(name = "deceasedDate") DateParam deceasedDate) {
    patientService.killPatient(thePatientId.getIdPart(), deceasedDate.getValue().toInstant());
    return new MethodOutcome();
  }



  /**
   * The getResourceType method comes from IResourceProvider, and must be overridden to indicate
   * what type of resource this provider supplies.
   */
  @Override
  public Class<Patient> getResourceType() {
    return Patient.class;
  }

  /**
   * This is the "read" operation. The "@Read" annotation indicates that this method supports the
   * read and/or vread operation.
   * <p>
   * Read operations take a single parameter annotated with the {@link IdParam} paramater, and
   * should return a single resource instance.
   * </p>
   *
   * @param theId The read operation takes one parameter, which must be of type IdDt and must be
   *        annotated with the "@Read.IdParam" annotation.
   * @return Returns a resource matching this identifier, or null if none exists.
   */
  @Read
  public Patient readPatient(@IdParam IdType theId) {
    return patientService.readPatient(theId);

  }


}
