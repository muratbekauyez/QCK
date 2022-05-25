package com.example.qck.repository;

import com.example.qck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User getUserByUsername(String username);
}
