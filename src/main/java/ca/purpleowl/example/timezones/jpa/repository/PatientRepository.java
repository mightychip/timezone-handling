package ca.purpleowl.example.timezones.jpa.repository;

import ca.purpleowl.example.timezones.jpa.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
