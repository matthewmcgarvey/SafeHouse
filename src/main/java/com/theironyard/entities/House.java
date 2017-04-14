package com.theironyard.entities;

import com.theironyard.services.HouseHoldItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private User user;

    @Autowired
    private static HouseHoldItemRepository houseHoldItems;

    public House(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public House() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addItem(Item item) {
        houseHoldItems.save(new HouseHoldItem(this.id, item));
    }
}
