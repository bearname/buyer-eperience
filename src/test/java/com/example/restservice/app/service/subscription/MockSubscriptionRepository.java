package com.example.restservice.app.service.subscription;

import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.repository.SubscriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class MockSubscriptionRepository implements SubscriptionRepository {
    @Override
    public long getCountByItemIdAndUserId(String itemId, BigInteger userId) {
        return 0;
    }

    @Override
    public Subscription findByItemIdAndUserId(String itemId, BigInteger userId) {
        return null;
    }

    @Override
    public BigInteger getCountOfAllNeededToCheckItems() {
        return null;
    }

    @Override
    public List<Item> getNeededCheckByPage(Pageable pageable) {
        return null;
    }

    @Override
    public Subscription findByItemIdAndUserEmail(String itemId, String email) {
        return null;
    }

    @Override
    public Page<Subscription> following(BigInteger userId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Object[]> getSubscribers(String itemId) {
        return null;
    }

    @Override
    public Iterable<Subscription> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Subscription> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends Subscription> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Subscription> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Subscription> findAll() {
        return null;
    }

    @Override
    public Iterable<Subscription> findAllById(Iterable<Long> longs) {
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
    public void delete(Subscription entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Subscription> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
