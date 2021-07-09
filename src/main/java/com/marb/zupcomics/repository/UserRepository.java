package com.marb.zupcomics.repository;

import com.marb.zupcomics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrCpf(String email, String cpf);
}
