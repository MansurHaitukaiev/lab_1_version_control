package ua.opnu.labwork4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ua.opnu.labwork4.registration.model.RegistrationStatus;

import java.time.LocalDate;

@Data
@Schema(description = "Відповідь з даними про реєстрацію")
public class RegistrationResponse {
    @Schema(description = "ID реєстрації", example = "1")
    private Long id;

    @Schema(description = "Дата створення", example = "2026-06-01")
    private LocalDate registrationDate;

    @Schema(description = "Поточний статус", example = "PENDING")
    private RegistrationStatus status;

    @Schema(description = "Подія, на яку створено заявку")
    private EventResponse event;

    @Schema(description = "Учасник, який подав заявку")
    private ParticipantResponse participant;
}