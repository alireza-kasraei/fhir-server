package net.devk.fhir.api;

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
