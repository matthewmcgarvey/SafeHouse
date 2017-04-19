package com.theironyard.entities;

import com.theironyard.services.HouseHoldItemRepository;
import com.theironyard.services.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class Items {
    private ItemRepository itemRepo;
    private HouseHoldItemRepository hhRepo;

    @Autowired
    public Items(ItemRepository itemRepo, HouseHoldItemRepository hhRepo) {
        this.itemRepo = itemRepo;
        this.hhRepo = hhRepo;
    }

    public List<HouseHoldItem> findByHouseId(Integer houseId) {
        return hhRepo.findByHouseId(houseId);
    }

    public void save(HouseHoldItem hhItem) {
        hhRepo.save(hhItem);
    }

    public void deleteByHouseIdAndItem_Id(Integer houseId, Integer itemId) {
        hhRepo.deleteByHouseIdAndItem_Id(houseId, itemId);
    }

    public void deleteByHouseId(Integer houseId) {
        hhRepo.deleteByHouseId(houseId);
    }

    public Item findByAsin(String asin) {
        return itemRepo.findByAsin(asin);
    }

    public HouseHoldItem findHhItemByHouseIdAndItem_Id(Integer houseId, Integer itemId) {
        return hhRepo.findByHouseIdAndItem_Id(houseId, itemId);
    }

    public boolean updateHhItemHouseId(Integer itemId, Integer fromHouseId, Integer toHouseId) {
        HouseHoldItem hhItem = hhRepo.findOne(itemId);
        if (hhItem != null && hhItem.getHouseId() == fromHouseId) {
            HouseHoldItem hhItemCheck = hhRepo.findByHouseIdAndItem_Id(toHouseId, hhItem.getItem().getId());
            if (hhItemCheck == null) {
                hhItem.setHouseId(toHouseId);
                HouseHoldItem updatedHhItem = hhRepo.save(hhItem);
                return updatedHhItem.getHouseId() == toHouseId;
            }
        }
        return false;
    }

}
