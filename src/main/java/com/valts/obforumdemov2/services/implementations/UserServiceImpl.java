package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.repositories.UserRepository;
import com.valts.obforumdemov2.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario " + email +  " no existe"));
//    }

    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario " + email +  " no existe"));
    }
}
