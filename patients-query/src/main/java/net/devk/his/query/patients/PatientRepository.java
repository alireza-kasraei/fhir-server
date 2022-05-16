package net.devk.his.query.patients;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface PatientRepository extends JpaRepository<PatientEntity, UUID> {

}
