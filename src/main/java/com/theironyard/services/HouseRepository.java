package com.theironyard.services;

import com.theironyard.entities.House;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface HouseRepository extends CrudRepository<House, Integer> {
    @Transactional
    void deleteByIdAndUser_Id(Integer houseId, Integer userId);

    List<House> findByUser_Id(Integer userId);
}
