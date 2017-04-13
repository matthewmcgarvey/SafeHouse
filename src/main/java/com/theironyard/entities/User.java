package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    public User(String name, String password) throws IllegalArgumentException {
        setName(name);
        setPassword(password);
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty(value = "username")
    public String getName() {
        return name;
    }

    public boolean setName(String name) throws IllegalArgumentException {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return true;
        }
        throw new IllegalArgumentException();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) throws IllegalArgumentException {
        if (password != null && !password.isEmpty()) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
            return true;
        }
        throw new IllegalArgumentException();
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
