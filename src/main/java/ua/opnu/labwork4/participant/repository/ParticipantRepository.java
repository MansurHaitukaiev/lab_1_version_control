package ua.opnu.labwork4.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork4.participant.model.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByEmail(String email);

    // /events/{id}/participants
    List<Participant> findByEventsId(Long eventId);

    // /search/participants?query=
    List<Participant> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email);
}