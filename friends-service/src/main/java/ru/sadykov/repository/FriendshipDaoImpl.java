package ru.sadykov.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sadykov.entity.Friendship;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final EntityManager em;

    @Override
    public Optional<Friendship> findByTargetUserAndSourceUser(Long targetUser, Long sourceUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Friendship> cq = cb.createQuery(Friendship.class);

        Root<Friendship> friendship = cq.from(Friendship.class);

        Predicate friendshipPredicateOne = cb.and(
                cb.equal(friendship.get("sourceUser"), targetUser),
                cb.equal(friendship.get("targetUser"), sourceUser)
        );
        Predicate friendshipPredicateTwo = cb.and(
                cb.equal(friendship.get("targetUser"), targetUser),
                cb.equal(friendship.get("sourceUser"), sourceUser)
        );
        Predicate friendshipPredicate = cb.or(friendshipPredicateOne, friendshipPredicateTwo);
        cq.where(friendshipPredicate);

        TypedQuery<Friendship> query = em.createQuery(cq);
        List<Friendship> singleResult = query.getResultList();

        if (singleResult.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(singleResult.get(0));
        }
    }
}
