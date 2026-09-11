package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.opnu.labwork4.registration.model.RegistrationStatus;

@Data
@Schema(description = "Запит на швидку зміну лише статусу реєстрації")
public class RegistrationStatusRequest {

    @Schema(description = "Новий статус реєстрації (наприклад, CONFIRMED або CANCELLED)", example = "CONFIRMED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Статус обов'язковий")
    private RegistrationStatus status;
}