package com.agribridge.agribridge_backend.repository;

import com.agribridge.agribridge_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (for registration)
    boolean existsByEmail(String email);

    // Find all farmers with pending approval
    List<User> findByRoleAndStatus(User.Role role, User.Status status);
}