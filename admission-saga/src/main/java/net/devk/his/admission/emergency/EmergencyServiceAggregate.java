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
import net.devk.fhir.api.AdmissionCreatedEvent;
import net.devk.fhir.api.CreateNewAdmissionCommand;
import net.devk.fhir.api.EmergencyServiceEndedEvent;
import net.devk.fhir.api.EmergencyServiceStartedEvent;
import net.devk.fhir.api.EndEmergencyServiceCommand;
import net.devk.fhir.api.StartEmergencyServiceCommand;

@Aggregate
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
    apply(new AdmissionCreatedEvent(command.getAdmissionId(), UUID.randomUUID().toString(),
        command.getPatientName(), Instant.now()));
  }

  @CommandHandler
  public void handle(StartEmergencyServiceCommand command) {
    apply(new EmergencyServiceStartedEvent(command.getAdmissionId(), Instant.now()));
  }

  @CommandHandler
  public void handle(EndEmergencyServiceCommand command) {
    apply(new EmergencyServiceEndedEvent(command.getAdmissionId(), Instant.now()));
  }

  @EventSourcingHandler
  public void on(AdmissionCreatedEvent event) {
    this.addmissionId = event.getAdmissionId();
    this.arrivalDate = event.getDate();
    this.practitionerName = nextPractitioner();
  }

  @EventSourcingHandler
  public void on(EmergencyServiceStartedEvent event) {
    this.serviceDate = event.getServiceDate();
  }

  @EventSourcingHandler
  public void on(EmergencyServiceEndedEvent event) {
    this.endDate = event.getDate();
  }

  private static String nextPractitioner() {
    return practitioners.get(random.nextInt(practitioners.size() - 1));
  }



}
