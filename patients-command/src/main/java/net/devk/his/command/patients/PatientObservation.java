package net.devk.his.command.patients;

import org.axonframework.modelling.command.EntityId;

public class PatientObservation {

  @EntityId
  private String observationId;

  private String status;
  private String code;

  public PatientObservation(String observationId, String status, String code) {
    super();
    this.observationId = observationId;
    this.status = status;
    this.code = code;
  }

  public String getObservationId() {
    return observationId;
  }

  public void setObservationId(String observationId) {
    this.observationId = observationId;
  }



}
