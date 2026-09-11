package ua.opnu.labwork4.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork4.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}