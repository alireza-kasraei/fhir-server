package net.devk.his.admission.emergency;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import net.devk.fhir.api.commands.CreateNewAdmissionCommand;
import net.devk.fhir.api.commands.EndEmergencyServiceCommand;
import net.devk.fhir.api.commands.StartEmergencyServiceCommand;

@RestController
@RequestMapping("/emergency")
@RequiredArgsConstructor
public class EmergencyServiceController {

  private final CommandGateway commandGateway;

  @PostMapping
  @ResponseBody
  public ResponseEntity<Map<String, String>> createAdmission(
      @RequestBody Map<String, String> data) {
    String admissionId = UUID.randomUUID().toString();
    commandGateway.send(new CreateNewAdmissionCommand(admissionId, data.get("patientName")));
    return ResponseEntity.ok(Collections.singletonMap("admissionId", admissionId));
  }

  @PostMapping("{admissionId}/start")
  @ResponseBody
  public ResponseEntity<?> startAdmission(@PathVariable("admissionId") String admissionId) {
    commandGateway.sendAndWait(new StartEmergencyServiceCommand(admissionId));
    return ResponseEntity.ok().build();
  }

  @PostMapping("{admissionId}/end")
  @ResponseBody
  public ResponseEntity<?> endAdmission(@PathVariable("admissionId") String admissionId) {
    commandGateway.sendAndWait(new EndEmergencyServiceCommand(admissionId));
    return ResponseEntity.ok().build();
  }

}
