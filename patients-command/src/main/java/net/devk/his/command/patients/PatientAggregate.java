package net.devk.his.command.patients;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import java.time.Instant;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.extern.slf4j.Slf4j;
import net.devk.fhir.api.commands.CreateNewPatientCommand;
import net.devk.fhir.api.commands.ExpirePatientCommand;
import net.devk.fhir.api.events.PatientCreatedEvent;
import net.devk.fhir.api.events.PatientExpiredEvent;

@Aggregate
@Slf4j
public class PatientAggregate {

  @AggregateIdentifier
  private String patientId;
  private String name;
  private Instant deceasedDate;


  @CommandHandler
  public PatientAggregate(CreateNewPatientCommand command) {
    log.info("creating a patient aggregate:{}", command);
    // is it a valid patient?
    apply(new PatientCreatedEvent(command.getPatientId(), command.getName()));
  }

  @CommandHandler
  public void handle(ExpirePatientCommand command) {
    if (command.getDate().isAfter(Instant.now())) {
      throw new IllegalArgumentException("Haaan!?");
    }
    log.info("expiring a patient:{}", command);
    apply(new PatientExpiredEvent(command.getPatientId(), command.getDate()));
  }

  @EventSourcingHandler
  public void on(PatientCreatedEvent event) {
    log.info("setting PatientAggregate values with:{}", event);
    this.patientId = event.getPatientId();
    this.name = event.getName();
  }

  @EventSourcingHandler
  public void on(PatientExpiredEvent event) {
    log.info("patient expired on :{}", event);
    this.deceasedDate = event.getDate();
  }

  public PatientAggregate() {
    // Required by Axon to construct an empty instance to initiate Event Sourcing.
  }
}

