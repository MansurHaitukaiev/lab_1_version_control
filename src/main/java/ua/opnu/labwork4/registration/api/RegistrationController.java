package ua.opnu.labwork4.registration.api;

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
import ua.opnu.labwork4.dto.request.RegistrationCreateRequest;
import ua.opnu.labwork4.dto.request.RegistrationStatusRequest;
import ua.opnu.labwork4.dto.request.RegistrationUpdateRequest;
import ua.opnu.labwork4.dto.response.RegistrationResponse;
import ua.opnu.labwork4.exception.ApiErrorResponse;
import ua.opnu.labwork4.mapper.AppMapper;
import ua.opnu.labwork4.registration.model.Registration;
import ua.opnu.labwork4.registration.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@Tag(name = "Реєстрації", description = "Управління процесом заявок на події (оформлення, зміна статусу, скасування)")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppMapper mapper;

    public RegistrationController(RegistrationService registrationService, AppMapper mapper) {
        this.registrationService = registrationService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Оформити реєстрацію", description = "Реєструє учасника на подію. Не можна створювати дублікати реєстрацій для одного й того ж учасника на ту саму подію.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Реєстрація успішна", content = @Content(schema = @Schema(implementation = RegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Подію або учасника не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Учасник вже зареєстрований на цей захід", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<RegistrationResponse> addRegistration(@Valid @RequestBody RegistrationCreateRequest request) {
        Registration registration = mapper.toEntity(request);
        Registration saved = registrationService.createRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всі реєстрації", description = "Повертає список усіх оформлених заявок.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    public ResponseEntity<List<RegistrationResponse>> getRegistrations() {
        List<RegistrationResponse> responses = registrationService.getAllRegistrations().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти реєстрацію за ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Реєстрацію знайдено"),
            @ApiResponse(responseCode = "404", description = "Реєстрацію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<RegistrationResponse> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(mapper.toResponse(registration));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Повне оновлення реєстрації", description = "Дозволяє оновити дату та статус реєстрації.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Реєстрацію оновлено"),
            @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Реєстрацію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<RegistrationResponse> updateRegistration(@PathVariable Long id, @Valid @RequestBody RegistrationUpdateRequest request) {
        Registration updatedData = mapper.toEntity(request);
        Registration updated = registrationService.updateRegistration(id, updatedData);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Оновити статус реєстрації", description = "Швидка зміна статусу заявки (наприклад, підтвердження або скасування).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус оновлено"),
            @ApiResponse(responseCode = "400", description = "Некоректний статус", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Реєстрацію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<RegistrationResponse> updateRegistrationStatus(@PathVariable Long id, @Valid @RequestBody RegistrationStatusRequest request) {
        Registration updated = registrationService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити реєстрацію", description = "Видаляє заявку учасника на подію.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Реєстрацію успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Реєстрацію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}