package ua.opnu.labwork4.event.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork4.dto.request.EventCreateRequest;
import ua.opnu.labwork4.dto.request.EventUpdateRequest;
import ua.opnu.labwork4.dto.response.EventResponse;
import ua.opnu.labwork4.dto.response.ParticipantResponse;
import ua.opnu.labwork4.dto.response.RegistrationResponse;
import ua.opnu.labwork4.event.model.Event;
import ua.opnu.labwork4.event.service.EventService;
import ua.opnu.labwork4.exception.ApiErrorResponse;
import ua.opnu.labwork4.mapper.AppMapper;
import ua.opnu.labwork4.registration.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Події", description = "Керування конференціями: планування заходів, прив'язка категорій, перегляд списків відвідувачів")
public class EventController {

    private final EventService eventService;
    private final RegistrationService registrationService;
    private final AppMapper mapper;

    public EventController(EventService eventService, RegistrationService registrationService, AppMapper mapper) {
        this.eventService = eventService;
        this.registrationService = registrationService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Створити нову подію", description = "Планує новий захід. Дата події не може бути в минулому. Переданий id організатора повинен існувати.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Подію успішно створено", content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації або дата в минулому", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Вказаного організатора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<EventResponse> addEvent(@Valid @RequestBody EventCreateRequest request) {
        Event event = mapper.toEntity(request);
        Event saved = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всі події", description = "Повертає перелік усіх запланованих подій у системі.")
    @ApiResponse(responseCode = "200", description = "Список подій успішно отримано")
    public ResponseEntity<List<EventResponse>> getEvents() {
        List<EventResponse> responses = eventService.getAllEvents().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти подію за ID", description = "Повертає розгорнуту інформацію про подію (організатор, категорії, учасники).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Подію знайдено"),
            @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<EventResponse> getEventsById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(mapper.toResponse(event));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані події", description = "Оновлює параметри події з перевіркою валідації.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Подію успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Подію або організатора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @Valid @RequestBody EventUpdateRequest request) {
        Event updatedEvent = eventService.updateEvent(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(updatedEvent));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити подію", description = "Видаляє подію. Видалення заборонено, якщо на подію є активні реєстрації.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Подію успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: на подію існують активні реєстрації", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Отримати події за категорією", description = "Шукає всі події, які належать до конкретної категорії.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список подій успішно отримано")
    })
    public ResponseEntity<List<EventResponse>> getEventsByCategory(@PathVariable Long categoryId) {
        List<EventResponse> events = eventService.getEventsByCategory(categoryId).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}/participants")
    @Operation(summary = "Отримати учасників події", description = "Повертає список людей, які зареєстровані на цю подію.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список учасників успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<List<ParticipantResponse>> getEventParticipants(@PathVariable Long id) {
        List<ParticipantResponse> participants = eventService.getEventParticipants(id).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/categories/{categoryId}")
    @Operation(summary = "Прив'язати подію до категорії", description = "Додає існуючу категорію до вказаної події.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно додано до події"),
            @ApiResponse(responseCode = "404", description = "Подію або категорію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<EventResponse> addCategoryToEvent(@PathVariable Long id, @PathVariable Long categoryId) {
        Event updatedEvent = eventService.addCategoryToEvent(id, categoryId);
        return ResponseEntity.ok(mapper.toResponse(updatedEvent));
    }

    @DeleteMapping("/{id}/categories/{categoryId}")
    @Operation(summary = "Видалити категорію у події", description = "Відв'язує категорію від події.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно відв'язано"),
            @ApiResponse(responseCode = "404", description = "Подію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<EventResponse> removeCategoryFromEvent(@PathVariable Long id, @PathVariable Long categoryId) {
        Event updatedEvent = eventService.removeCategoryFromEvent(id, categoryId);
        return ResponseEntity.ok(mapper.toResponse(updatedEvent));
    }

    @GetMapping("/{id}/registrations")
    @Operation(summary = "Отримати реєстрації на подію", description = "Повертає список реєстраційних заявок для вказаної події.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список реєстрацій успішно отримано")
    })
    public ResponseEntity<List<RegistrationResponse>> getEventRegistrations(@PathVariable Long id) {
        List<RegistrationResponse> registrations = registrationService.getEventRegistrations(id).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(registrations);
    }
}