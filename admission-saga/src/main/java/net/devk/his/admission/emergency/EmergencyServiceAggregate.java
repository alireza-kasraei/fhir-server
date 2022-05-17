package net.devk.his.admission.emergency;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.extern.slf4j.Slf4j;
import net.devk.fhir.api.commands.CreateNewAdmissionCommand;
import net.devk.fhir.api.commands.EndEmergencyServiceCommand;
import net.devk.fhir.api.commands.StartEmergencyServiceCommand;
import net.devk.fhir.api.events.AdmissionCreatedEvent;
import net.devk.fhir.api.events.EmergencyServiceEndedEvent;
import net.devk.fhir.api.events.EmergencyServiceStartedEvent;

@Aggregate
@Slf4j
public class EmergencyServiceAggregate {

  @AggregateIdentifier
  private String addmissionId;
  private Instant arrivalDate;
  private String practitionerName;
  private Instant serviceDate;
  private Instant endDate;

  private static Random random = new Random();

  private static List<String> practitioners =
      List.of("Ali", "Balder", "Maarten", "Anubhav", "Peter", "Kevin", "Vasi");

  public EmergencyServiceAggregate() {}

  @CommandHandler
  public EmergencyServiceAggregate(CreateNewAdmissionCommand command) {
    log.info("New Emergency Service Received,{}", command);
    apply(new AdmissionCreatedEvent(command.getAdmissionId(), UUID.randomUUID().toString(),
        command.getPatientName(), Instant.now()));
  }

  @CommandHandler
  public void handle(StartEmergencyServiceCommand command) {
    if (practitionerName != null) {
      log.info("patient is in the {} office", practitionerName);
      throw new IllegalArgumentException();
    }
    log.info("start the Emergency Service,{}", command);
    apply(new EmergencyServiceStartedEvent(command.getAdmissionId(), Instant.now(),
        nextPractitioner()));
  }

  @CommandHandler
  public void handle(EndEmergencyServiceCommand command) {
    if (endDate != null) {
      log.info("patient discharged already");
      throw new IllegalArgumentException();
    }
    log.info("finish the Emergency Service,{}", command);
    apply(new EmergencyServiceEndedEvent(command.getAdmissionId(), Instant.now()));
  }

  @EventSourcingHandler
  public void on(AdmissionCreatedEvent event) {
    log.info("got a new emergency service,{}", event);
    this.addmissionId = event.getAdmissionId();
    this.arrivalDate = event.getDate();
  }

  @EventSourcingHandler
  public void on(EmergencyServiceStartedEvent event) {
    log.info("emergency service started,{}", event);
    this.serviceDate = event.getServiceDate();
    this.practitionerName = event.getPractitionerName();
  }

  @EventSourcingHandler
  public void on(EmergencyServiceEndedEvent event) {
    log.info("emergency service finished,{}", event);
    this.endDate = event.getDate();
  }

  private static String nextPractitioner() {
    return practitioners.get(random.nextInt(practitioners.size() - 1));
  }



}
