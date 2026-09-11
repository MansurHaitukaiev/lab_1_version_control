package ua.opnu.labwork4.category.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork4.category.model.Category;
import ua.opnu.labwork4.category.service.CategoryService;
import ua.opnu.labwork4.dto.request.CategoryCreateRequest;
import ua.opnu.labwork4.dto.request.CategoryUpdateRequest;
import ua.opnu.labwork4.dto.response.CategoryResponse;
import ua.opnu.labwork4.exception.ApiErrorResponse;
import ua.opnu.labwork4.mapper.AppMapper;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Категорії", description = "Управління тематичними категоріями для класифікації подій (наприклад: IT, Бізнес, Мистецтво)")
public class CategoryController {

    private final CategoryService categoryService;
    private final AppMapper mapper;

    public CategoryController(CategoryService categoryService, AppMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Створити нову категорію", description = "Додає до системи нову тематичну категорію.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категорію успішно створено", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних (наприклад, занадто коротка назва)", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryCreateRequest request) {
        Category category = mapper.toEntity(request);
        Category saved = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати список категорій", description = "Повертає список усіх доступних категорій у базі даних.")
    @ApiResponse(responseCode = "200", description = "Успішне отримання списку")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> responses = categoryService.getAllCategories().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти категорію за ID", description = "Отримує детальну інформацію про конкретну категорію.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію знайдено"),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(mapper.toResponse(category));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити категорію", description = "Дозволяє змінити назву та опис існуючої категорії.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категорію успішно оновлено"),
            @ApiResponse(responseCode = "400", description = "Помилка валідації", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) {
        Category updated = categoryService.updateCategory(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити категорію", description = "Видаляє категорію. Видалення заборонено, якщо до категорії прив'язані активні події (спрацює захист Conflict).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Категорію успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Категорію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: існують події з цією категорією", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}