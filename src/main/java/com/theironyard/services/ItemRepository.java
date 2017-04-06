package com.theironyard.services;

import com.theironyard.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    @Override
    List<Item> findAll();
}
