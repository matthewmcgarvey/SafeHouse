package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "households")
public class Household {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String houseName;

    @Column
    Integer rating;

    @ManyToOne
    Item item;

    @ManyToOne
    User user;
}
