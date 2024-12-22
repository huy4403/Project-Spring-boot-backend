package com.huy.aristino.repository;

import com.huy.aristino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    User findUserByUsernameAndPassword(String username, String password);
}
