package com.theironyard.entities;

import com.theironyard.services.HouseHoldItemRepository;
import com.theironyard.services.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class Items {
    @Autowired
    private static ItemRepository itemRepo;
    @Autowired
    private static HouseHoldItemRepository hhRepo;

    public static List<HouseHoldItem> findByHouseId(Integer houseId) {
        return hhRepo.findByHouseId(houseId);
    }

    public static void save(HouseHoldItem hhItem) {
        hhRepo.save(hhItem);
    }

    public static void deleteByHouseIdAndItem_Id(Integer houseId, Integer itemId) {
        hhRepo.deleteByHouseIdAndItem_Id(houseId, itemId);
    }

}
