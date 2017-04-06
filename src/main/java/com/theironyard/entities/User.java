package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return;
        }
        throw new IllegalArgumentException();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if (password != null && !password.isEmpty()) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        }
        throw new IllegalArgumentException();
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
