package ua.opnu.labwork4.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@Schema(description = "Стандартизована структура відповіді сервера при виникненні помилки")
public class ApiErrorResponse {

    @Schema(description = "Точний час виникнення помилки на сервері", example = "2026-06-01T16:10:57")
    private LocalDateTime timestamp;

    @Schema(description = "Цифровий HTTP статус-код помилки", example = "400")
    private Integer status;

    @Schema(description = "Офіційне текстове найменування HTTP-статусу", example = "Bad Request")
    private String error;

    @Schema(description = "Загальне повідомлення про причину збою", example = "Помилка валідації вхідних даних")
    private String message;

    @Schema(description = "Відносний URL-шлях запиту, на якому стався збій", example = "/participants")
    private String path;

    @Schema(description = "Мапа з деталізацією помилок для кожного невалідного поля (додається лише при статусі 400)",
            example = "{\"email\": \"Некоректний формат email\"}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> validationErrors;
}