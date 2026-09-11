package ua.opnu.labwork4.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork4.dto.response.EventResponse;
import ua.opnu.labwork4.dto.response.ParticipantResponse;
import ua.opnu.labwork4.event.service.EventService;
import ua.opnu.labwork4.mapper.AppMapper;
import ua.opnu.labwork4.participant.service.ParticipantService;

import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "Пошук", description = "Глобальний пошук по подіях та учасниках з можливістю фільтрації")
public class SearchController {

    private final EventService eventService;
    private final ParticipantService participantService;
    private final AppMapper mapper;

    public SearchController(EventService eventService, ParticipantService participantService, AppMapper mapper) {
        this.eventService = eventService;
        this.participantService = participantService;
        this.mapper = mapper;
    }

    @GetMapping("/events")
    @Operation(summary = "Базовий пошук подій", description = "Шукає події за ключовим словом (в назві або описі).")
    public ResponseEntity<List<EventResponse>> searchEvents(@RequestParam(required = false) String query) {
        List<EventResponse> events = eventService.searchEvents(query).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/advanced")
    @Operation(summary = "Розширений пошук подій", description = "Шукає події за ключовим словом, датою та локацією одночасно.")
    public ResponseEntity<List<EventResponse>> advancedSearchEvents(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String location) {
        List<EventResponse> events = eventService.advancedSearch(query, date, location).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/participants")
    @Operation(summary = "Пошук учасників", description = "Шукає учасників за ім'ям, прізвищем або email.")
    public ResponseEntity<List<ParticipantResponse>> searchParticipants(@RequestParam(required = false) String query) {
        List<ParticipantResponse> participants = participantService.searchParticipants(query).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(participants);
    }
}