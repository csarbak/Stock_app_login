package com.okta.developer.jugtours.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findUsersByStocksId(Long stockId);
}