package com.okta.developer.jugtours.config;

import com.okta.developer.jugtours.model.Stock;
import com.okta.developer.jugtours.model.StockRepository;
import com.okta.developer.jugtours.model.User;
import com.okta.developer.jugtours.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final StockRepository repository;
    private final UserRepository userRepository;

    public Initializer(StockRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository=userRepository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Seattle JUG", "Denver JUG", "Dublin JUG",
                "London JUG").forEach(name->{
            Stock stock = new Stock();
            stock.setTitle(name);
            stock.addUser(new User());
            repository.save(stock);
        });
        repository.findAll().forEach(System.out::println);
    }
}