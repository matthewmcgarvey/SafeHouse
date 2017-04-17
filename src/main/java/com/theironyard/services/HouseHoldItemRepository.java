package com.theironyard.services;

import com.theironyard.entities.HouseHoldItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface HouseHoldItemRepository extends CrudRepository<HouseHoldItem, Integer> {
    @Transactional
    void deleteByHouseIdAndItem_Id(Integer itemId, Integer houseId);
}
