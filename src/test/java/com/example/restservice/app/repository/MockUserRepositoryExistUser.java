package com.example.restservice.app.repository;

import com.example.restservice.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.util.Optional;

public class  MockUserRepositoryExistUser implements UserRepository {
    @Override
    public boolean existsByEmail(String email) {
        return true;
    }

    @Override
    public User getById(BigInteger id) {
        return null;
    }

    @Override
    public Optional<User> findById(BigInteger id) {
        return Optional.empty();
    }

    @Override
    public User getByEmail(String email) {
        return new User(email);
//        return new User(BigInteger.valueOf(1L), email, false);
    }

    @Override
    public Iterable<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
