package com.okta.developer.jugtours.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public User(){
    }
    public User(String id, String name, String email){
        this.id=id;
        this.name=name;
        this.email=email;
    }
    @Id
    private String id;
    private String name;
    private String email;
}
