package ua.opnu.labwork4.mapper;

import org.springframework.stereotype.Component;
import ua.opnu.labwork4.category.model.Category;
import ua.opnu.labwork4.dto.request.*;
import ua.opnu.labwork4.dto.response.*;
import ua.opnu.labwork4.event.model.Event;
import ua.opnu.labwork4.organizer.model.Organizer;
import ua.opnu.labwork4.participant.model.Participant;
import ua.opnu.labwork4.registration.model.Registration;

@Component
public class AppMapper {

    // CATEGORY MAPPERS
    public Category toEntity(CategoryCreateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }

    public Category toEntity(CategoryUpdateRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    // ORGANIZER MAPPERS
    public Organizer toEntity(OrganizerCreateRequest request) {
        Organizer org = new Organizer();
        org.setFirstName(request.getFirstName());
        org.setLastName(request.getLastName());
        org.setEmail(request.getEmail());
        org.setOrganization(request.getOrganization());
        return org;
    }

    public OrganizerResponse toResponse(Organizer org) {
        OrganizerResponse response = new OrganizerResponse();
        response.setId(org.getId());
        response.setFirstName(org.getFirstName());
        response.setLastName(org.getLastName());
        response.setEmail(org.getEmail());
        response.setOrganization(org.getOrganization());
        return response;
    }

    public Organizer toEntity(OrganizerUpdateRequest request) {
        Organizer org = new Organizer();
        org.setFirstName(request.getFirstName());
        org.setLastName(request.getLastName());
        org.setEmail(request.getEmail());
        org.setOrganization(request.getOrganization());
        return org;
    }

    // PARTICIPANT MAPPERS
    public Participant toEntity(ParticipantCreateRequest request) {
        Participant p = new Participant();
        p.setFirstName(request.getFirstName());
        p.setLastName(request.getLastName());
        p.setEmail(request.getEmail());
        p.setPhone(request.getPhone());
        return p;
    }

    public Participant toEntity(ParticipantUpdateRequest request) {
        Participant p = new Participant();
        p.setFirstName(request.getFirstName());
        p.setLastName(request.getLastName());
        p.setEmail(request.getEmail());
        p.setPhone(request.getPhone());
        return p;
    }

    public ParticipantResponse toResponse(Participant p) {
        ParticipantResponse res = new ParticipantResponse();
        res.setId(p.getId());
        res.setFirstName(p.getFirstName());
        res.setLastName(p.getLastName());
        res.setEmail(p.getEmail());
        res.setPhone(p.getPhone());
        return res;
    }

    // EVENT MAPPERS
    public Event toEntity(EventCreateRequest request) {
        Event e = new Event();
        e.setTitle(request.getTitle());
        e.setDescription(request.getDescription());
        e.setDate(request.getDate());
        e.setLocation(request.getLocation());

        if (request.getOrganizerId() != null) {
            Organizer org = new Organizer();
            org.setId(request.getOrganizerId());
            e.setOrganizer(org);
        }
        return e;
    }

    public Event toEntity(EventUpdateRequest request) {
        Event e = new Event();
        e.setTitle(request.getTitle());
        e.setDescription(request.getDescription());
        e.setDate(request.getDate());
        e.setLocation(request.getLocation());

        if (request.getOrganizerId() != null) {
            Organizer org = new Organizer();
            org.setId(request.getOrganizerId());
            e.setOrganizer(org);
        }
        return e;
    }

    public EventResponse toResponse(Event e) {
        EventResponse res = new EventResponse();
        res.setId(e.getId());
        res.setTitle(e.getTitle());
        res.setDescription(e.getDescription());
        res.setDate(e.getDate());
        res.setLocation(e.getLocation());

        if (e.getOrganizer() != null) {
            res.setOrganizer(toResponse(e.getOrganizer()));
        }
        if (e.getCategories() != null) {
            res.setCategories(e.getCategories().stream().map(this::toResponse).toList());
        }
        if (e.getParticipants() != null) {
            res.setParticipants(e.getParticipants().stream().map(this::toResponse).toList());
        }
        return res;
    }

    // REGISTRATION MAPPERS
    public Registration toEntity(RegistrationCreateRequest request) {
        Registration r = new Registration();
        r.setRegistrationDate(request.getRegistrationDate());
        r.setStatus(request.getStatus());

        if (request.getEventId() != null) {
            Event e = new Event();
            e.setId(request.getEventId());
            r.setEvent(e);
        }
        if (request.getParticipantId() != null) {
            Participant p = new Participant();
            p.setId(request.getParticipantId());
            r.setParticipant(p);
        }
        return r;
    }

    public Registration toEntity(RegistrationUpdateRequest request) {
        Registration r = new Registration();
        r.setRegistrationDate(request.getRegistrationDate());
        r.setStatus(request.getStatus());
        return r;
    }

    public RegistrationResponse toResponse(Registration r) {
        RegistrationResponse res = new RegistrationResponse();
        res.setId(r.getId());
        res.setRegistrationDate(r.getRegistrationDate());
        res.setStatus(r.getStatus());

        if (r.getEvent() != null) {
            res.setEvent(toResponse(r.getEvent()));
        }
        if (r.getParticipant() != null) {
            res.setParticipant(toResponse(r.getParticipant()));
        }
        return res;
    }
}