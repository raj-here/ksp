package com.ksp.timesheet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksp.timesheet.entity.User;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long id);

    User findByUsername(String username);

    void deleteByUsername(String username);
}