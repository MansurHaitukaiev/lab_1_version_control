package ua.opnu.labwork4.category.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork4.category.model.Category;
import ua.opnu.labwork4.category.repository.CategoryRepository;
import ua.opnu.labwork4.event.repository.EventRepository;
import ua.opnu.labwork4.exception.ConflictOperationException;
import ua.opnu.labwork4.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public CategoryService(CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Категорію з ID " + id + " не знайдено"));
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        // не можна видаляти сутність, якщо з нею пов’язані активні записи
        if (!eventRepository.findByCategoriesId(id).isEmpty()) {
            throw new ConflictOperationException("Не можна видалити категорію, оскільки існують події з цією категорією");
        }
        categoryRepository.delete(category);
    }

    public Category updateCategory(Long id, Category updated) {
        Category category = getCategoryById(id);
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        return categoryRepository.save(category);
    }
}