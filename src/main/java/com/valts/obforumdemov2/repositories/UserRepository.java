package com.valts.obforumdemov2.repositories;

import com.valts.obforumdemov2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    @Query(value = "select u from User u left join fetch u.followedTemas ft left join ft.curso where u.id = :userId")
//    Optional<User> findByIdWithFollowedTemas(Long userId);
}
