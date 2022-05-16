package net.devk.fhir.api;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDiedEvent {

  private String patientId;
  private Instant date;

}
