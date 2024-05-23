package com.okta.developer.jugtours.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByPublished(boolean published);

    List<Stock> findByTitleContaining(String title);

    List<Stock> findStocksByUsersId(String userId);
}
