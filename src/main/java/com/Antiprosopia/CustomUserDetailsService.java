package com.Antiprosopia;

import com.Antiprosopia.citizen.CitizenRepository;
import com.Antiprosopia.dealership.DealershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CitizenRepository citizenRepository;
    private final DealershipRepository dealershipRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public CustomUserDetailsService(CitizenRepository citizenRepository, DealershipRepository dealershipRepository, JwtUtil jwtUtil) {
        this.citizenRepository = citizenRepository;
        this.dealershipRepository = dealershipRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String afm) throws UsernameNotFoundException {
        // Έλεγχος για Citizen
        var citizen = citizenRepository.findByAfm(afm);
        if (citizen.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    citizen.get().getAfm(),
                    citizen.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CITIZEN"))
            );
        }

        // Έλεγχος για Dealership
        var dealership = dealershipRepository.findByAfm(afm);
        if (dealership.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    dealership.get().getAfm(),
                    dealership.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_DEALERSHIP"))
            );
        }

        throw new UsernameNotFoundException("User not found with AFM: " + afm);
    }

    public AuthenticationResponse login(String afm, String password) throws Exception {
        UserDetails userDetails = loadUserByUsername(afm);

        // Ελέγχουμε αν το password αντιστοιχεί
        if (userDetails.getPassword().equals(password)) {
            // Αντιστοιχούμε τον ρόλο του χρήστη
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority())
                    .orElse("ROLE_UNKNOWN");

            // Δημιουργούμε το JWT με τον ρόλο

            String token = jwtUtil.generateToken(afm, role);
            return AuthenticationResponse.builder().token(token).message("Welcome").build();
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}
