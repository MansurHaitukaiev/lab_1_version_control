package ua.opnu.labwork4.analytics.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork4.category.model.Category;
import ua.opnu.labwork4.category.repository.CategoryRepository;
import ua.opnu.labwork4.event.repository.EventRepository;
import ua.opnu.labwork4.participant.repository.ParticipantRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final CategoryRepository categoryRepository;

    public AnalyticsService(EventRepository eventRepository, ParticipantRepository participantRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.categoryRepository = categoryRepository;
    }

    public long getEventsCount() {
        return eventRepository.count();
    }

    public long getParticipantsCount() {
        return participantRepository.count();
    }

    public long getUpcomingEventsCount() {
        return eventRepository.countByDateAfter(LocalDate.now());
    }

    public long getPastEventsCount() {
        return eventRepository.countByDateBefore(LocalDate.now());
    }

    public Map<String, Integer> getEventsByCategory() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, Integer> stats = new HashMap<>();

        for (Category c : categories) {
            stats.put(c.getName(), c.getEvents().size());
        }
        return stats;
    }
}