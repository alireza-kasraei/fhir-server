package net.devk.fhir.api.commands;

import java.time.Instant;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpirePatientCommand {

  @TargetAggregateIdentifier
  private String patientId;
  private Instant date;


}