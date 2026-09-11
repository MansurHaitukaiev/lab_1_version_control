package ua.opnu.labwork4.organizer.api;

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
import ua.opnu.labwork4.dto.request.OrganizerCreateRequest;
import ua.opnu.labwork4.dto.request.OrganizerUpdateRequest;
import ua.opnu.labwork4.dto.response.EventResponse;
import ua.opnu.labwork4.dto.response.OrganizerResponse;
import ua.opnu.labwork4.exception.ApiErrorResponse;
import ua.opnu.labwork4.mapper.AppMapper;
import ua.opnu.labwork4.organizer.model.Organizer;
import ua.opnu.labwork4.organizer.service.OrganizerService;

import java.util.List;

@RestController
@RequestMapping("/organizers")
@Tag(name = "Організатори", description = "Управління профілями компаній та осіб, що виступають організаторами подій")
public class OrganizerController {

    private final OrganizerService organizerService;
    private final AppMapper mapper;

    public OrganizerController(OrganizerService organizerService, AppMapper mapper) {
        this.organizerService = organizerService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Створити організатора", description = "Реєструє нового організатора в системі. Email повинен бути унікальним.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Організатора успішно створено", content = @Content(schema = @Schema(implementation = OrganizerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Організатор з таким email вже існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<OrganizerResponse> addOrganizer(@Valid @RequestBody OrganizerCreateRequest request) {
        Organizer organizer = mapper.toEntity(request);
        Organizer saved = organizerService.createOrganizer(organizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати список організаторів", description = "Повертає список усіх зареєстрованих організаторів.")
    @ApiResponse(responseCode = "200", description = "Успішно отримано")
    public ResponseEntity<List<OrganizerResponse>> getOrganizers() {
        List<OrganizerResponse> responses = organizerService.getAllOrganizers().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти організатора за ID", description = "Отримує детальну інформацію про організатора за його ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Організатора знайдено"),
            @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<OrganizerResponse> getOrganizerById(@PathVariable Long id) {
        Organizer organizer = organizerService.getOrganizerById(id);
        return ResponseEntity.ok(mapper.toResponse(organizer));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані організатора", description = "Змінює ім'я, організацію або email (з перевіркою на унікальність).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профіль успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Новий email вже зайнятий", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<OrganizerResponse> updateOrganizer(@PathVariable Long id, @Valid @RequestBody OrganizerUpdateRequest request) {
        Organizer updated = organizerService.updateOrganizer(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити організатора", description = "Видаляє організатора. Неможливо видалити, якщо він має створені події.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Організатора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: організатор має пов'язані події", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteOrganizer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/events")
    @Operation(summary = "Отримати події організатора", description = "Повертає список усіх подій, створених цим організатором.")
    @ApiResponse(responseCode = "200", description = "Список подій успішно отримано")
    public ResponseEntity<List<EventResponse>> getOrganizerEvents(@PathVariable Long id) {
        List<EventResponse> responses = organizerService.getOrganizerEvents(id).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}