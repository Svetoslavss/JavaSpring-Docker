package com.academy.UserRestApi.repository;

import com.academy.UserRestApi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users> findByUsername(String username);
    List<Users> findUserByEmail(String email);
}
