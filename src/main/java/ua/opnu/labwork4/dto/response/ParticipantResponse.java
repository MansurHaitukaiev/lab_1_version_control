package ua.opnu.labwork4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Відповідь з профілем учасника")
public class ParticipantResponse {
    @Schema(description = "ID учасника", example = "1")
    private Long id;

    @Schema(description = "Ім'я", example = "Іван")
    private String firstName;

    @Schema(description = "Прізвище", example = "Іванов")
    private String lastName;

    @Schema(description = "Email", example = "ivan.tech@example.com")
    private String email;

    @Schema(description = "Телефон", example = "+380501234567")
    private String phone;
}