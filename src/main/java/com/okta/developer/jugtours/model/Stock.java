package com.okta.developer.jugtours.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "stock_users", joinColumns = { @JoinColumn(name = "stock_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();

    public Stock() {

    }

    public Stock(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean isPublished) {
        this.published = isPublished;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getStocks().add(this);
    }

    public void removeUser(long userId) {
        User user = this.users.stream().filter(t -> t.getId() == userId).findFirst().orElse(null);
        if (user != null) {
            this.users.remove(user);
            user.getStocks().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Stock [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }

}
