package com.okta.developer.jugtours.config;

import com.okta.developer.jugtours.model.Stock;
import com.okta.developer.jugtours.model.StockRepository;
import com.okta.developer.jugtours.model.User;
import com.okta.developer.jugtours.model.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
//        Stream.of("Seattle JUG", "Denver JUG", "Dublin JUG",
//                "London JUG").forEach(name->{
//            Stock stock = new Stock();
//            stock.setLogo(name);
//            stock.setCurrentPrice(new BigDecimal("69.00"));
//            User user = new User();
//            user.setId(RandomStringUtils.randomAlphanumeric(10));
//            stock.addUser(user);
//            repository.save(stock);
//        });

        Stock stock = new Stock();
        stock.setLogo("test");
        Stock nstock = new Stock();
        nstock.setLogo("test2");
        User user = new User();
        user.setId("test");
        User nuser = new User();
        nuser.setId("test3");
//        stock.addUser(user);
//        stock.addUser(nuser);
        nstock.addUser(user);
        repository.save(stock);
        repository.save(nstock);

        repository.findAll().forEach(System.out::println);
    }
}