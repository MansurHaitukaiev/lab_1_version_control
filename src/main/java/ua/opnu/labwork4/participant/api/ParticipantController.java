package ua.opnu.labwork4.participant.api;

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
import ua.opnu.labwork4.dto.request.ParticipantCreateRequest;
import ua.opnu.labwork4.dto.request.ParticipantUpdateRequest;
import ua.opnu.labwork4.dto.response.EventResponse;
import ua.opnu.labwork4.dto.response.ParticipantResponse;
import ua.opnu.labwork4.exception.ApiErrorResponse;
import ua.opnu.labwork4.mapper.AppMapper;
import ua.opnu.labwork4.participant.model.Participant;
import ua.opnu.labwork4.participant.service.ParticipantService;

import java.util.List;

@RestController
@RequestMapping("/participants")
@Tag(name = "Учасники", description = "Управління профілями учасників конференцій: реєстрація, оновлення контактів, перегляд подій")
public class ParticipantController {

    private final ParticipantService participantService;
    private final AppMapper mapper;

    public ParticipantController(ParticipantService participantService, AppMapper mapper) {
        this.participantService = participantService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Зареєструвати нового учасника", description = "Створює новий профіль учасника в системі. Email повинен бути унікальним.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Учасника успішно створено", content = @Content(schema = @Schema(implementation = ParticipantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Учасник з таким email вже існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ParticipantResponse> addParticipant(@Valid @RequestBody ParticipantCreateRequest request) {
        Participant participant = mapper.toEntity(request);
        Participant saved = participantService.createParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всіх учасників", description = "Повертає повний список зареєстрованих учасників у системі.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public ResponseEntity<List<ParticipantResponse>> getParticipants() {
        List<ParticipantResponse> responses = participantService.getAllParticipants().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти учасника за ID", description = "Повертає профіль конкретного учасника за його унікальним ідентифікатором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Учасника знайдено"),
            @ApiResponse(responseCode = "404", description = "Учасника з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ParticipantResponse> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(mapper.toResponse(participant));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані учасника", description = "Дозволяє змінити ім'я, телефон або email учасника за ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профіль успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Помилка валідації даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Учасника не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Новий email вже зайнятий іншим користувачем", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ParticipantResponse> updateParticipant(@PathVariable Long id, @Valid @RequestBody ParticipantUpdateRequest request) {
        Participant updated = participantService.updateParticipant(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити профіль учасника", description = "Видаляє учасника з бази. Заборонено видаляти, якщо він має активні реєстрації на події.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Учасника успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Учасника не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: учасник має активні реєстрації", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/events")
    @Operation(summary = "Отримати події учасника", description = "Повертає список усіх конференцій та заходів, на які зареєстрований цей учасник.")
    @ApiResponse(responseCode = "200", description = "Список подій успішно згенеровано")
    public ResponseEntity<List<EventResponse>> getParticipantEvents(@PathVariable Long id) {
        List<EventResponse> events = participantService.getParticipantEvents(id).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(events);
    }
}