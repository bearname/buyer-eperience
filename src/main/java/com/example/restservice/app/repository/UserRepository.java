package com.example.restservice.app.repository;

import com.example.restservice.app.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    boolean existsByEmail(String email);

    User getById(BigInteger id);
    Optional<User> findById(BigInteger id);
    User getByEmail(String email);
}
