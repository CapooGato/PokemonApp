package com.example.pokemonapp.api.security;

import com.example.pokemonapp.api.models.Role;
import com.example.pokemonapp.api.models.UserEntity;
import com.example.pokemonapp.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klas która pobiera użytkowników z bazy danych, loadUserByUsername() zwraca obiekt UserDetail, który jest potem
 * używany do stworzenia Authentication a następnie Authentication wrzucany do SecurityContext
 * */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new User(user.getUsername(), user.getPassword(), mapRolesToGrantedAuthories(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuthories(List<Role> roles){
        return roles.stream().map((r) -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
