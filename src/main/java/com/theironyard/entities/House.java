package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean defaultHouse = false;

    @Column
    private String color;

    public House(String name, String color) {
        this.name = name;
        this.color = color;
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

    public Boolean getDefaultHouse() {
        return defaultHouse;
    }

    public void setDefaultHouse(Boolean defaultHouse) {
        this.defaultHouse = defaultHouse;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
