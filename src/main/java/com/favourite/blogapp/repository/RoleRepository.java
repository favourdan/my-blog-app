package com.favourite.blogapp.repository;

import com.favourite.blogapp.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Roles , Long> {
    Optional<Roles> findByName(String name);
}
