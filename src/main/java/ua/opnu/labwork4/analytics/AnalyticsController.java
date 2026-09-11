package ua.opnu.labwork4.analytics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork4.analytics.service.AnalyticsService;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Аналітика", description = "Збір статистики по подіях, категоріях та учасниках системи")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/events/count")
    @Operation(summary = "Загальна кількість подій", description = "Повертає загальну кількість усіх створених подій у базі даних.")
    public ResponseEntity<Long> getEventsCount() {
        return ResponseEntity.ok(analyticsService.getEventsCount());
    }

    @GetMapping("/participants/count")
    @Operation(summary = "Загальна кількість учасників", description = "Повертає кількість усіх зареєстрованих у системі учасників.")
    public ResponseEntity<Long> getParticipantsCount() {
        return ResponseEntity.ok(analyticsService.getParticipantsCount());
    }

    @GetMapping("/events/upcoming")
    @Operation(summary = "Кількість майбутніх подій", description = "Підраховує події, дата яких ще не настала.")
    public ResponseEntity<Long> getUpcomingEventsCount() {
        return ResponseEntity.ok(analyticsService.getUpcomingEventsCount());
    }

    @GetMapping("/events/past")
    @Operation(summary = "Кількість минулих подій", description = "Підраховує події, дата яких вже минула.")
    public ResponseEntity<Long> getPastEventsCount() {
        return ResponseEntity.ok(analyticsService.getPastEventsCount());
    }

    @GetMapping("/events/by-category")
    @Operation(summary = "Статистика подій за категоріями", description = "Повертає мапу, де ключ - назва категорії, а значення - кількість подій у ній.")
    public ResponseEntity<Map<String, Integer>> getEventsByCategory() {
        return ResponseEntity.ok(analyticsService.getEventsByCategory());
    }
}