package ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sadykov.entity.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipDao {

}
