package net.devk.his.admission.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import net.devk.fhir.api.commands.CreateNewPatientCommand;
import net.devk.fhir.api.events.AdmissionCreatedEvent;
import net.devk.fhir.api.events.EmergencyServiceEndedEvent;
import net.devk.fhir.api.events.EmergencyServiceStartedEvent;

@Saga
@Slf4j
public class AdmissionSaga {

  private boolean paid = false;
  private boolean visited = false;

  @Autowired
  private transient CommandGateway commandGateway;



  @StartSaga
  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(AdmissionCreatedEvent event) {
    log.info("admission saga started,{}", event);
    SagaLifecycle.associateWith("patientId", event.getPatientId());
    commandGateway.send(new CreateNewPatientCommand(event.getPatientId(), event.getPatientName()));
  }

  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(EmergencyServiceStartedEvent event) {
    if (!visited) {
      log.info("{} is visiting the patient ...", event.getPractitionerName());
      visited = true;
    } else {
      log.info("patient has been visited by,{}", event.getPractitionerName());
    }
  }

  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(EmergencyServiceEndedEvent event) {
    // did he pay?
    paid = true;
    if (paid) {
      log.info("patient paid the invoce and discharged,{}", event);
      SagaLifecycle.end();
    }
  }


}
