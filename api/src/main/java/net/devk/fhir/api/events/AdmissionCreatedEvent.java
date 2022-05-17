package net.devk.fhir.api.events;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionCreatedEvent {

  private String admissionId;
  private String patientId;
  private String patientName;
  private Instant date;



}
