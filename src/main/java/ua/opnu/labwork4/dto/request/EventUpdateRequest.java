package ua.opnu.labwork4.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Запит на оновлення існуючої події")
public class EventUpdateRequest {

    @Schema(description = "Назва події", example = "Spring Boot Майстер-клас", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва обов'язкова")
    @Size(min = 2, max = 150, message = "Назва повинна містити від 2 до 150 символів")
    private String title;

    @Schema(description = "Опис події", example = "Повне занурення у розробку REST API", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Опис обов'язковий")
    @Size(min = 10, max = 2000, message = "Опис повинен містити від 10 до 2000 символів")
    private String description;

    @Schema(description = "Дата проведення (не в минулому)", example = "2026-10-15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата обов'язкова")
    private LocalDate date;

    @Schema(description = "Локація або онлайн-посилання", example = "Київ", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Локація обов'язкова")
    @Size(min = 5, max = 200, message = "Локація повинна містити від 5 до 200 символів")
    private String location;

    @Schema(description = "ID організатора", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID організатора обов'язкове")
    private Long organizerId;
}