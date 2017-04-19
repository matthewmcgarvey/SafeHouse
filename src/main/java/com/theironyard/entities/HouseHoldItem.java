package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "house_hold_items")
public class HouseHoldItem {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer houseId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Item item;

    public HouseHoldItem(Integer houseId, Item item) {
        this.houseId = houseId;
        this.item = item;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
