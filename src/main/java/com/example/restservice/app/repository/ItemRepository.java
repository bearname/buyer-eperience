package com.example.restservice.app.repository;


import com.example.restservice.app.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
    boolean existsById(String id);

    Item getById(String id);
}
