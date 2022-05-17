package net.devk.fhir.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartEmergencyServiceCommand {

  @TargetAggregateIdentifier
  private String admissionId;

}
