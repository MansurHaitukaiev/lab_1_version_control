package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запит на оновлення профілю організатора події")
public class OrganizerUpdateRequest {

    @Schema(description = "Ім'я організатора", example = "Олександр", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Ім'я обов'язкове")
    @Size(min = 2, max = 50, message = "Ім'я повинно містити від 2 до 50 символів")
    private String firstName;

    @Schema(description = "Прізвище організатора", example = "Шевченко", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Прізвище обов'язкове")
    @Size(min = 2, max = 50, message = "Прізвище повинно містити від 2 до 50 символів")
    private String lastName;

    @Schema(description = "Робочий email (повинен бути унікальним)", example = "alex.shevchenko@tech-ukraine.ua", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Некоректний формат email")
    @Size(max = 100, message = "Максимальна довжина email 100 символів")
    private String email;

    @Schema(description = "Назва організації або компанії", example = "Tech Ukraine", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва організації обов'язкова")
    @Size(min = 2, max = 150, message = "Назва організації повинна містити від 2 до 150 символів")
    private String organization;
}