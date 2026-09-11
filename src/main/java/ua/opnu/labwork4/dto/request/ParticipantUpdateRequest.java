package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запит на оновлення профілю учасника")
public class ParticipantUpdateRequest {

    @Schema(description = "Ім'я учасника", example = "Іван", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Ім'я обов'язкове")
    @Size(min = 2, max = 50, message = "Ім'я повинно містити від 2 до 50 символів")
    private String firstName;

    @Schema(description = "Прізвище учасника", example = "Іванов", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Прізвище обов'язкове")
    @Size(min = 2, max = 50, message = "Прізвище повинно містити від 2 до 50 символів")
    private String lastName;

    @Schema(description = "Електронна пошта (унікальна)", example = "ivan.tech@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Некоректний формат email")
    @Size(max = 100, message = "Максимальна довжина email 100 символів")
    private String email;

    @Schema(description = "Контактний телефон", example = "+380501234567", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Телефон обов'язковий")
    @Size(min = 10, max = 20, message = "Телефон повинен містити від 10 до 20 символів")
    @Pattern(regexp = "^[0-9\\+\\s]+$", message = "Допускаються лише цифри, пробіли та символ +")
    private String phone;
}