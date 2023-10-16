package ru.sadykov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sadykov.entity.BinaryContent;

public interface BinaryContentRepository extends MongoRepository<BinaryContent, String> {
}
