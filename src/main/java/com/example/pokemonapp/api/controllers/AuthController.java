package com.example.pokemonapp.api.controllers;

import com.example.pokemonapp.api.dto.RegisterDto;
import com.example.pokemonapp.api.models.Role;
import com.example.pokemonapp.api.models.UserEntity;
import com.example.pokemonapp.api.repository.RoleRepository;
import com.example.pokemonapp.api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping(value = "register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        /*This prob shouldn't look like this, every person on register has role "USER" now, also ".get()" is not good idea */
        Role roles = roleRepository.findByName("USER").get();// .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Account created successfully", HttpStatus.OK);
    }
}
