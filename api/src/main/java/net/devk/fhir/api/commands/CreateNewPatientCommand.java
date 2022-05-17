package net.devk.fhir.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewPatientCommand {

  @TargetAggregateIdentifier
  private String patientId;
  private String name;



}
