package com.example.diplomska.repository;

import com.example.diplomska.model.Role;
import com.example.diplomska.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    @Modifying
    @Query("update User set role = :role where email = :email")
    void updateUserRole(@Param("email") String email, @Param("role") Role role);
}
