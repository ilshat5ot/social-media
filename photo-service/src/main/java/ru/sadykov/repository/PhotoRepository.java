package ru.sadykov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.sadykov.entity.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
}
