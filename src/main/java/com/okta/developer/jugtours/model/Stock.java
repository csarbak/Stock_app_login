package com.okta.developer.jugtours.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
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

    @Column(name = "currentPrice")
    private BigDecimal currentPrice;

    @Column(name = "logo")
    private String logo;

    @Column(name = "published")
    private boolean published;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
                CascadeType.ALL })
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
        if(user.getId().isEmpty() || user.getId().isBlank() || user.getId()==null){
            return;
        }
        this.users.add(user);
        user.getStocks().add(this);
    }

    public void removeUser(String userId) {
        User user = this.users.stream().filter(t -> Objects.equals(t.getId(), userId)).findFirst().orElse(null);
        if (user != null) {
            this.users.remove(user);
            user.getStocks().remove(this);
        }
    }
    public void removeUser(User user) {
        if (user != null) {
            this.users.remove(user);
            user.getStocks().remove(this);
        }
    }
    @Override
    public String toString() {
        return "Stock [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
