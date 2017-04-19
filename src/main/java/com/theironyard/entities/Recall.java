package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "recalls")
public class Recall {

    @Id
    private int recallId;

    @Column
    private int recallNumber;

    @Column
    private String recallDate;

    @Column
    private String description;

    @Column
    private String url;

    @Column
    private String title;

    @ManyToOne
    private Item item;

    public Recall() {
    }

    public Recall(int recallId, int recallNumber, String recallDate, String description, String url, String title) {
        this.recallId = recallId;
        this.recallNumber = recallNumber;
        this.recallDate = recallDate;
        this.description = description;
        this.url = url;
        this.title = title;
    }


    public int getRecallId() {
        return recallId;
    }

    public void setRecallId(int recallId) {
        this.recallId = recallId;
    }

    public int getRecallNumber() {
        return recallNumber;
    }

    public void setRecallNumber(int recallNumber) {
        this.recallNumber = recallNumber;
    }

    public String getRecallDate() {
        return recallDate;
    }

    public void setRecallDate(String recallDate) {
        this.recallDate = recallDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}