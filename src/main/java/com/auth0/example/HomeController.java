package com.auth0.example;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the home page.
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<?> home(Object o) {
        System.out.println("object : "+ o.toString());
//        if (principal != null) {
//            model.addAttribute("profile", principal.getClaims());
//            System.out.println( "PRINCPLE: \n:" +principal.getClaims().toString() );
//
//        }
//        System.out.println( "MODEL: \n:" + model.asMap().toString());
//        System.out.println( "PRINCPLE: \n:" +principal.getClaims().toString() );
        return ResponseEntity.ok().body("controller");
    }
}