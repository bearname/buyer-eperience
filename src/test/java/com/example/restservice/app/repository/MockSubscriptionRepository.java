package com.example.restservice.app.repository;

import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockSubscriptionRepository implements SubscriptionRepository{
    public static final  List<Item> ITEMS = new ArrayList<>();
    static  {
        ITEMS.add(new Item("1", 2500, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286"));
        ITEMS.add(new Item("2", 2500, "https://www.avito.ru/yoshkar-ola/noutbuki/noutbuk_hp_14s-fq0070ur_1738034102"));
    }

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
        return BigInteger.valueOf(2);
    }

    @Override
    public List<Item> getNeededCheckByPage(Pageable pageable) {
        return ITEMS;
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
        List<Object[]> result = new ArrayList<>();
        final Object[] o = new Object[3];
        final User user = new User(BigInteger.valueOf(1), "test@email.com", true);
        o[0] = user.getId();
        o[1] = user.getEmail();
        o[2] = user.isVerified();
        result.add(o);
        return result;
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
        return null;
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