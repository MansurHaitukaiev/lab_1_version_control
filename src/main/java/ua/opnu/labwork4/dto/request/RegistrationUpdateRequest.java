package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.opnu.labwork4.registration.model.RegistrationStatus;

import java.time.LocalDate;

@Data
@Schema(description = "Запит на оновлення даних реєстрації")
public class RegistrationUpdateRequest {

    @Schema(description = "Нова дата реєстрації", example = "2026-06-02", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата реєстрації обов'язкова")
    private LocalDate registrationDate;

    @Schema(description = "Новий статус", example = "CONFIRMED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Статус обов'язковий")
    private RegistrationStatus status;
}