package net.devk.fhir.api;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientExpiredEvent {

  private String patientId;
  private Instant date;

}
