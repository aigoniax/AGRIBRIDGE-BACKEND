package com.agribridge.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRoleAndStatus(String role, String status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET photo = :photo WHERE email = :email", nativeQuery = true)
    void updatePhoto(@Param("email") String email, @Param("photo") byte[] photo);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.fullName = :fullName, u.phone = :phone, u.location = :location WHERE u.email = :email")
    void updateProfile(@Param("email") String email, @Param("fullName") String fullName, @Param("phone") String phone, @Param("location") String location);
}