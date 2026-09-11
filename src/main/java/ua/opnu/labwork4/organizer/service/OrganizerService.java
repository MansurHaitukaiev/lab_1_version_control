package ua.opnu.labwork4.organizer.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork4.event.model.Event;
import ua.opnu.labwork4.event.repository.EventRepository;
import ua.opnu.labwork4.exception.ConflictOperationException;
import ua.opnu.labwork4.exception.DuplicateResourceException;
import ua.opnu.labwork4.exception.ResourceNotFoundException;
import ua.opnu.labwork4.organizer.model.Organizer;
import ua.opnu.labwork4.organizer.repository.OrganizerRepository;

import java.util.List;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;

    public OrganizerService(OrganizerRepository organizerRepository, EventRepository eventRepository) {
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
    }

    public Organizer createOrganizer(Organizer organizer) {
        // не можна створювати записи з email, який уже існує
        if (organizerRepository.existsByEmail(organizer.getEmail())) {
            throw new DuplicateResourceException("Організатор з таким email вже існує в системі");
        }
        return organizerRepository.save(organizer);
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    public Organizer getOrganizerById(Long id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Організатора з ID " + id + " не знайдено"));
    }

    public Organizer updateOrganizer(Long id, Organizer updated) {
        Organizer org = getOrganizerById(id);

        // не можна змінити email, на email який вже привʼязаний до запису
        if (!org.getEmail().equals(updated.getEmail()) && organizerRepository.existsByEmail(updated.getEmail())) {
            throw new DuplicateResourceException("Організатор з таким email вже існує в системі");
        }

        org.setFirstName(updated.getFirstName());
        org.setLastName(updated.getLastName());
        org.setEmail(updated.getEmail());
        org.setOrganization(updated.getOrganization());
        return organizerRepository.save(org);
    }

    public void deleteOrganizer(Long id) {
        Organizer org = getOrganizerById(id);
        // заборона видалення, якщо є пов'язані події
        if (!eventRepository.findByOrganizerId(id).isEmpty()) {
            throw new ConflictOperationException("Не можна видалити організатора, оскільки він має створені події");
        }
        organizerRepository.delete(org);
    }

    public List<Event> getOrganizerEvents(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }
}