package ua.opnu.labwork4.registration.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork4.event.model.Event;
import ua.opnu.labwork4.event.repository.EventRepository;
import ua.opnu.labwork4.exception.DuplicateResourceException;
import ua.opnu.labwork4.exception.ResourceNotFoundException;
import ua.opnu.labwork4.participant.model.Participant;
import ua.opnu.labwork4.participant.repository.ParticipantRepository;
import ua.opnu.labwork4.registration.model.Registration;
import ua.opnu.labwork4.registration.model.RegistrationStatus;
import ua.opnu.labwork4.registration.repository.RegistrationRepository;

import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               EventRepository eventRepository,
                               ParticipantRepository participantRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    public Registration createRegistration(Registration registration) {
        if (registration.getStatus() == null) {
            registration.setStatus(RegistrationStatus.PENDING);
        }

        // перевірка існування пов'язаних сутностей
        Event event = eventRepository.findById(registration.getEvent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Подію не знайдено"));
        Participant participant = participantRepository.findById(registration.getParticipant().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Учасника не знайдено"));

        // не можна створювати дублікати основних операцій
        if (registrationRepository.existsByEventIdAndParticipantId(event.getId(), participant.getId())) {
            throw new DuplicateResourceException("Цей учасник вже зареєстрований на цю подію");
        }

        registration.setEvent(event);
        registration.setParticipant(participant);

        if (!event.getParticipants().contains(participant)) {
            event.getParticipants().add(participant);
            eventRepository.save(event);
        }

        return registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Реєстрацію з ID " + id + " не знайдено"));
    }

    public Registration updateStatus(Long id, RegistrationStatus status) {
        Registration reg = getRegistrationById(id);
        reg.setStatus(status);
        return registrationRepository.save(reg);
    }

    public void deleteRegistration(Long id) {
        Registration reg = getRegistrationById(id);
        registrationRepository.delete(reg);
    }

    public List<Registration> getEventRegistrations(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public Registration updateRegistration(Long id, Registration updated) {
        Registration reg = getRegistrationById(id);
        reg.setRegistrationDate(updated.getRegistrationDate());
        reg.setStatus(updated.getStatus());
        return registrationRepository.save(reg);
    }
}