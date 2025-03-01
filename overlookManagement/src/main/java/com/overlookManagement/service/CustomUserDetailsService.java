package com.overlookManagement.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.overlookManagement.exception.OurException;
import com.overlookManagement.repository.UserRepository;

// UserDetailsService is used by DaoAuthenticationProvider for retrieving a username, a password,
// and other attributes for authenticating with a username and password

// This is only used if the AuthenticationManagerBuilder has not been populated
// and no AuthenticationProviderBean is defined.

// remember to add @Service otherwise the clas won't be found in the classpath (cannot find symbol)
@Service
public class CustomUserDetailsService implements UserDetailsService{

   @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new OurException("Username/Email not Found"));
    }
}