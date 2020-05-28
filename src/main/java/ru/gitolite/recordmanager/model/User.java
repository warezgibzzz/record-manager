package ru.gitolite.recordmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    // SQLite uses sequence generator

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String password;
    private String salt;

    public User() {

    }

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return getName() + " (id: " + getId() + ")";
    }
}
