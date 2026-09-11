package ua.opnu.labwork4.actuator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
@Tag(name = "Моніторинг (Actuator)", description = "Системні ендпоінти для перевірки стану здоров'я та метрик сервера")
public class ActuatorController {

    @GetMapping("/health")
    @Operation(summary = "Перевірка статусу (Health)", description = "Повертає інформацію про те, чи працює сервіс нормально.")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("GET /actuator/health OK - Сервіс працює нормально");
    }

    @GetMapping("/metrics")
    @Operation(summary = "Базові метрики", description = "Повертає список доступних системних метрик.")
    public ResponseEntity<String> getMetrics() {
        return ResponseEntity.ok("GET /actuator/metrics OK - Список метрик");
    }

    @GetMapping("/prometheus")
    @Operation(summary = "Метрики Prometheus", description = "Повертає метрики у форматі, зрозумілому для системи моніторингу Prometheus.")
    public ResponseEntity<String> getPrometheus() {
        return ResponseEntity.ok("GET /actuator/prometheus OK - Метрики у форматі Prometheus");
    }
}