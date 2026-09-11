package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.opnu.labwork4.registration.model.RegistrationStatus;

import java.time.LocalDate;

@Data
@Schema(description = "Запит на створення заявки на реєстрацію")
public class RegistrationCreateRequest {

    @Schema(description = "Дата оформлення реєстрації", example = "2026-06-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата реєстрації обов'язкова")
    private LocalDate registrationDate;

    @Schema(description = "Статус реєстрації", example = "PENDING", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Статус обов'язковий")
    private RegistrationStatus status;

    @Schema(description = "ID події", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID події обов'язкове")
    private Long eventId;

    @Schema(description = "ID учасника", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID учасника обов'язкове")
    private Long participantId;
}