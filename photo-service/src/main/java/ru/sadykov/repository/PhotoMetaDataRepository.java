package ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sadykov.entity.PhotoMetaData;

public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Long> {
}
