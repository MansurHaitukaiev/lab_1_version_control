package ua.opnu.labwork4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Відповідь з даними про категорію")
public class CategoryResponse {
    @Schema(description = "Унікальний ID категорії", example = "1")
    private Long id;

    @Schema(description = "Назва категорії", example = "IT та Технології")
    private String name;

    @Schema(description = "Опис категорії", example = "Конференції, мітапи та хакатони для розробників")
    private String description;
}