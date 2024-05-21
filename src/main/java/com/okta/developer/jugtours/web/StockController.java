package com.okta.developer.jugtours.web;

import com.okta.developer.jugtours.model.Stock;
import com.okta.developer.jugtours.model.StockRepository;
import com.okta.developer.jugtours.model.User;
import com.okta.developer.jugtours.model.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class StockController {

    private final Logger log = LoggerFactory.getLogger(StockController.class);
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public StockController(StockRepository stockRepository, UserRepository userRepository) {
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stocks")
    Collection<Stock> stocks(Principal principal) {
        System.out.println("Prinple: " + principal.toString());
        return stockRepository.findAll();
        //return userRepository.findAll();
        //System.out.println("Prinple Name(ID): " + principal.getName());
        //return stockRepository.findStocksByBuyersId(principal.getName());
    }
    @GetMapping("/users")
    Collection<User> users(Principal principal) {
        System.out.println("Prinple: " + principal.toString());
        //return stockRepository.findAll();
        return userRepository.findAll();
        //System.out.println("Prinple Name(ID): " + principal.getName());
        //return stockRepository.findStocksByBuyersId(principal.getName());
    }
    @GetMapping("/stock/{id}")
    ResponseEntity<?> getStock(@PathVariable Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.map(response -> ResponseEntity.ok().body(response))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/stock")
    ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock,
                                      @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create stock: {}", stock);
//        Map<String, Object> details = principal.getAttributes();
//        String userId = details.get("sub").toString();

        // check to see if user already exists
//        Optional<User> user = userRepository.findById(userId);
//        stock.setUser(user.orElse(new User(userId,
//            details.get("name").toString(), details.get("email").toString())));

        Stock result = stockRepository.save(stock);
        return ResponseEntity.created(new URI("/api/stock/" + result.getId()))
            .body(result);
    }

    @PutMapping("/stock/{id}")
    ResponseEntity<Stock> updateStock(@Valid @RequestBody Stock stock) {
        log.info("Request to update stock: {}", stock);
        Stock result = stockRepository.save(stock);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable Long id) {
        log.info("Request to delete stock: {}", id);
        stockRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
