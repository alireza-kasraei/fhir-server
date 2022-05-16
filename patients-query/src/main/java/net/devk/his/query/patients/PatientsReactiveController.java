package net.devk.his.query.patients;

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import net.devk.fhir.api.FindAllPatientsQuery;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class PatientsReactiveController {

  private final ReactorQueryGateway queryGateway;

  @GetMapping(value = "/patients", produces = "text/event-stream")
  public Flux<PatientEntity> watchPatients() {
    return queryGateway.subscriptionQueryMany(new FindAllPatientsQuery(), PatientEntity.class);
  }

}
