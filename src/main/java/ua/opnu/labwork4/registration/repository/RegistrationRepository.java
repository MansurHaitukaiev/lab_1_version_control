package ua.opnu.labwork4.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork4.registration.model.Registration;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    boolean existsByEventIdAndParticipantId(Long eventId, Long participantId);
    boolean existsByParticipantId(Long participantId);
}