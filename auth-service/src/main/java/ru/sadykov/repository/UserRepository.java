package ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sadykov.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
