package com.theironyard.services;

import com.theironyard.entities.HouseHoldItem;
import org.springframework.data.repository.CrudRepository;

public interface HouseHoldItemRepository extends CrudRepository<HouseHoldItem, Integer> {
}
