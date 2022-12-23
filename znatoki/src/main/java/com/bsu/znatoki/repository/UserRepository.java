package com.bsu.znatoki.repository;

import com.bsu.znatoki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    @Query("select u.id from User u where u.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Modifying
    @Query("update User u set u.activated = :activated where u.id = :id")
    void setActivatedById(@Param("id") int id, @Param("activated") boolean activated);

    boolean existsByEmail(String email);
}
