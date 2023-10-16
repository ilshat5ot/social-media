package ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sadykov.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
