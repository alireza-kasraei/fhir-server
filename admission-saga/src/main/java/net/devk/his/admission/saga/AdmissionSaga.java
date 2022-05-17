package net.devk.his.admission.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import net.devk.fhir.api.AdmissionCreatedEvent;
import net.devk.fhir.api.CreateNewPatientCommand;
import net.devk.fhir.api.EmergencyServiceEndedEvent;
import net.devk.fhir.api.EmergencyServiceStartedEvent;

@Saga
public class AdmissionSaga {

  private boolean paid = false;
  private boolean visited = false;

  @Autowired
  private transient CommandGateway commandGateway;



  @StartSaga
  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(AdmissionCreatedEvent event) {
    SagaLifecycle.associateWith("patientId", event.getPatientId());
    commandGateway.send(new CreateNewPatientCommand(event.getPatientId(), event.getPatientName()));
  }

  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(EmergencyServiceStartedEvent event) {
    visited = true;
  }

  @SagaEventHandler(associationProperty = "admissionId")
  public void handle(EmergencyServiceEndedEvent event) {
    // did he pay?
    paid = true;
    if (paid) {
      SagaLifecycle.end();
    }
  }


}
