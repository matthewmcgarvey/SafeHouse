package com.theironyard.services;

import com.theironyard.entities.House;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseRepository extends CrudRepository<House, Integer> {
    @Override
    List<House> findAll();

    House findOneByNameAndUser_Name(String name, String username);
}
