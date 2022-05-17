package net.devk.fhir.api.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewAdmissionCommand {

  private String admissionId;
  private String patientName;

}
