package ru.sadykov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sadykov.entity.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
