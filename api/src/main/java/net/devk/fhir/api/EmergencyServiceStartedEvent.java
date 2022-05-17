package net.devk.fhir.api;

import java.time.Instant;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyServiceStartedEvent {
  @TargetAggregateIdentifier
  private String admissionId;
  private Instant serviceDate;
}
