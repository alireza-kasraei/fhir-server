package net.devk.his.query.patients;

import java.util.List;
import java.util.UUID;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import net.devk.fhir.api.FindAllPatientsQuery;
import net.devk.fhir.api.PatientCreatedEvent;
import net.devk.fhir.api.PatientExpiredEvent;

@Service
@ProcessingGroup("patients-summary")
public class PatientsProjection {

  private final QueryUpdateEmitter queryUpdateEmitter;
  private final PatientRepository patientRepository;

  public PatientsProjection(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") QueryUpdateEmitter queryUpdateEmitter,
      PatientRepository patientRepository) {
    this.queryUpdateEmitter = queryUpdateEmitter;
    this.patientRepository = patientRepository;
  }

  @EventHandler
  public void on(PatientCreatedEvent event) {
    PatientEntity patientEntity = new PatientEntity();
    patientEntity.setId(UUID.fromString(event.getPatientId()));
    patientEntity.setName(event.getName());
    patientRepository.save(patientEntity);

    queryUpdateEmitter.emit(FindAllPatientsQuery.class, query -> true, patientEntity);
  }

  @EventHandler
  public void on(PatientExpiredEvent event) {
    patientRepository.findById(UUID.fromString(event.getPatientId())).ifPresent(patientEntity -> {
      patientEntity.setDeceasedDate(event.getDate());
      patientRepository.save(patientEntity);
    });
  }

  @QueryHandler
  public List<PatientEntity> handle(FindAllPatientsQuery query) {
    return patientRepository.findAll();
  }

}
