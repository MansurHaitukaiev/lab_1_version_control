package ua.opnu.labwork4.organizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork4.organizer.model.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    boolean existsByEmail(String email);
}