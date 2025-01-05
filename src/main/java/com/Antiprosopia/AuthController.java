package com.Antiprosopia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestParam String afm, @RequestParam String password) {
        try {
            AuthenticationResponse token = userDetailsService.login(afm, password);
            return ResponseEntity.ok().body(token);
        } catch (BadCredentialsException e) {
            AuthenticationResponse resp = AuthenticationResponse.builder().token("").message("error").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
