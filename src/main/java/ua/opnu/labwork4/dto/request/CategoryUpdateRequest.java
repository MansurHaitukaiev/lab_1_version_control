package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запит на оновлення існуючої категорії")
public class CategoryUpdateRequest {

    @Schema(description = "Нова назва категорії", example = "Оновлені IT Технології", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва обов'язкова і не може бути порожньою")
    @Size(min = 2, max = 150, message = "Назва повинна містити від 2 до 150 символів")
    private String name;

    @Schema(description = "Новий опис категорії", example = "Оновлений опис для IT заходів", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Опис обов'язковий")
    @Size(min = 10, max = 2000, message = "Опис повинен містити від 10 до 2000 символів")
    private String description;
}