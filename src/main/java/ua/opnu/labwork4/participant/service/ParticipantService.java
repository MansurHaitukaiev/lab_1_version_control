package ua.opnu.labwork4.participant.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork4.event.model.Event;
import ua.opnu.labwork4.event.repository.EventRepository;
import ua.opnu.labwork4.exception.ConflictOperationException;
import ua.opnu.labwork4.exception.DuplicateResourceException;
import ua.opnu.labwork4.exception.ResourceNotFoundException;
import ua.opnu.labwork4.participant.model.Participant;
import ua.opnu.labwork4.participant.repository.ParticipantRepository;
import ua.opnu.labwork4.registration.repository.RegistrationRepository;

import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public ParticipantService(ParticipantRepository participantRepository, EventRepository eventRepository, RegistrationRepository registrationRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public Participant createParticipant(Participant participant) {
        // унікальний email
        if (participantRepository.existsByEmail(participant.getEmail())) {
            throw new DuplicateResourceException("Учасник з таким email вже існує");
        }
        return participantRepository.save(participant);
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Учасника з ID " + id + " не знайдено"));
    }

    public Participant updateParticipant(Long id, Participant updated) {
        Participant p = getParticipantById(id);

        if (!p.getEmail().equals(updated.getEmail()) && participantRepository.existsByEmail(updated.getEmail())) {
            throw new DuplicateResourceException("Учасник з таким email вже існує");
        }

        p.setFirstName(updated.getFirstName());
        p.setLastName(updated.getLastName());
        p.setEmail(updated.getEmail());
        p.setPhone(updated.getPhone());
        return participantRepository.save(p);
    }

    public void deleteParticipant(Long id) {
        Participant p = getParticipantById(id);
        if (registrationRepository.existsByParticipantId(id)) {
            throw new ConflictOperationException("Не можна видалити учасника, оскільки він має активні реєстрації");
        }
        participantRepository.delete(p);
    }

    public List<Event> getParticipantEvents(Long participantId) {
        return eventRepository.findByParticipantsId(participantId);
    }

    public List<Participant> searchParticipants(String query) {
        if (query == null || query.isBlank()) return getAllParticipants();
        return participantRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query);
    }
}