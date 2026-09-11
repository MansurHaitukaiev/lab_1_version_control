package ua.opnu.labwork4.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Відповідь з даними організатора")
public class OrganizerResponse {
    @Schema(description = "Унікальний ID", example = "1")
    private Long id;

    @Schema(description = "Ім'я", example = "Олександр")
    private String firstName;

    @Schema(description = "Прізвище", example = "Шевченко")
    private String lastName;

    @Schema(description = "Email", example = "alex.shevchenko@tech-ukraine.ua")
    private String email;

    @Schema(description = "Організація", example = "Tech Ukraine")
    private String organization;
}