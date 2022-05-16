package net.devk.his.query.patients;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PatientEntity {

  @Id
  private UUID id;
  private String name;
  private Instant deceasedDate;


}
