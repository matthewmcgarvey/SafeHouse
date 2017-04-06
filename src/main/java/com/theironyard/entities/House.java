package com.theironyard.entities;

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
    User user;

    @OneToMany(cascade = CascadeType.ALL) // cascades to save items
    @JoinTable(name = "household_items")  // creates a join table that links house id to item id
    private List<Item> items = new ArrayList<>();

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
