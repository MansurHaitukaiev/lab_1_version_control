package ua.opnu.labwork4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Комплексна відповідь з інформацією про подію")
public class EventResponse {
    @Schema(description = "ID події", example = "1")
    private Long id;

    @Schema(description = "Назва", example = "Spring Boot Майстер-клас")
    private String title;

    @Schema(description = "Опис", example = "Повне занурення у розробку REST API")
    private String description;

    @Schema(description = "Дата", example = "2026-10-15")
    private LocalDate date;

    @Schema(description = "Локація", example = "Київ")
    private String location;

    @Schema(description = "Організатор заходу")
    private OrganizerResponse organizer;

    @Schema(description = "Категорії події")
    private List<CategoryResponse> categories;

    @Schema(description = "Учасники події")
    private List<ParticipantResponse> participants;
}