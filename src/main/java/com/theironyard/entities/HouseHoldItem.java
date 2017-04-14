package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "house_hold_items")
public class HouseHoldItem {

    @Id
    @GeneratedValue
    private int id;

    @Column(updatable = false, insertable = false, nullable = false)
    private int houseId;

    @ManyToOne
    private Item itemInfo;

    public HouseHoldItem(int houseId, Item itemInfo) {
        this.houseId = houseId;
        this.itemInfo = itemInfo;
    }

    public HouseHoldItem() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public Item getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(Item itemInfo) {
        this.itemInfo = itemInfo;
    }
}
