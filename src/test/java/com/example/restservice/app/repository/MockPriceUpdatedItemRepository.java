package com.example.restservice.app.repository;

import com.example.restservice.app.model.Item;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.restservice.app.repository.MockSubscriptionRepository.ITEMS;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockPriceUpdatedItemRepository implements ItemRepository {
    @Override
    public <S extends Item> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Item> Iterable<S> saveAll(Iterable<S> entities) {
        final ArrayList<Item> result = new ArrayList<>();
        entities.forEach(result::add);

        assertEquals(result.size(), 1);
        if (result.get(0).getId().equals("1")) {
            final Item resultItem = result.get(0);
            final Item item2 = ITEMS.get(0);
            assertEquals(resultItem.getId(), item2.getId());
            assertEquals(resultItem.getPrice(), 2501);

            assertEquals(result.get(0), ITEMS.get(0));
        }

        return entities;
    }

    @Override
    public Optional<Item> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Iterable<Item> findAll() {
        return null;
    }

    @Override
    public Iterable<Item> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Item entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Item> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Item getById(String id) {
        return null;
    }
}
