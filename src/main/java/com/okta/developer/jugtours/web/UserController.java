package com.okta.developer.jugtours.web;

import com.okta.developer.jugtours.model.StockRepository;
import com.okta.developer.jugtours.model.User;
import com.okta.developer.jugtours.model.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final ClientRegistration registration;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    public UserController(ClientRegistrationRepository registrations, StockRepository stockRepository, UserRepository userRepository) {
        this.registration = registrations.findByRegistrationId("okta");
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User user) {
        //System.out.println("OAuth2User from userController: " + user.toString());
        if (user == null) {
            System.out.println("uer iz null");
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            Map<String, Object> details = user.getAttributes();
            String userId = details.get("sub").toString();
            Optional<User> u = userRepository.findById(userId);
            //System.out.println("U: " + u.toString());

            if(!u.isPresent()){
                User us = new User();
                us.setId(userId);
                System.out.println("detaials: " + details.toString());
                us.setName(details.get("name").toString());
                userRepository.save(us);
                return ResponseEntity.ok().body(us);
            }

            return ResponseEntity.ok().body(u.get());

            //return ResponseEntity.ok().body(user.getAttributes());
        }
    }
    @PutMapping("api/user")
    ResponseEntity<?> updateUser(@Valid @RequestBody User user, @AuthenticationPrincipal OAuth2User principal) {
        log.info("Request to update user: {}", user);
        //Check to see if all users are valid and remove ones not
if(user.getId().equals(principal.getName())){
    User result = userRepository.save(user);
    return ResponseEntity.ok().body(result);
}
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @DeleteMapping("api/user")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal OAuth2User principal) {

        Map<String, Object> details = principal.getAttributes();
        String id = details.get("sub").toString();
        log.info("Request to delete stock: {}", id);
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/api/users")
    Collection<User> users(Principal principal) {
        //System.out.println("Prinple: " + principal.toString());
        //return stockRepository.findAll();
        return userRepository.findAll();
        //System.out.println("Prinple Name(ID): " + principal.getName());
        //return stockRepository.findStocksByBuyersId(principal.getName());
    }
    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // send logout URL to client so they can initiate logout
        StringBuilder logoutUrl = new StringBuilder();
        String issuerUri = this.registration.getProviderDetails().getIssuerUri();
        logoutUrl.append(issuerUri.endsWith("/") ? issuerUri + "v2/logout" : issuerUri + "/v2/logout");
        logoutUrl.append("?client_id=").append(this.registration.getClientId());

        Map<String, String> logoutDetails = new HashMap<>();
        logoutDetails.put("logoutUrl", logoutUrl.toString());
        request.getSession(false).invalidate();
        return ResponseEntity.ok().body(logoutDetails);
    }
}
