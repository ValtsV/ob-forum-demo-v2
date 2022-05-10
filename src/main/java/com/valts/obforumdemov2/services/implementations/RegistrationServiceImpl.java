package com.valts.obforumdemov2.services.implementations;

import com.valts.obforumdemov2.dto.RegistrationDTO;
import com.valts.obforumdemov2.models.Role;
import com.valts.obforumdemov2.models.User;
import com.valts.obforumdemov2.repositories.RoleRepository;
import com.valts.obforumdemov2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String signUpUser(RegistrationDTO registrationDTO) {
        User user = new User();

        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setAvatar(registrationDTO.getAvatar());
        user.setPassword(registrationDTO.getPassword());

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        Role role = roleRepository.findRoleByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(user.getEmail().split("@")[1].equals("admin.com")){
            role = roleRepository.findRoleByName("ADMIN");
            roleSet.add(role);
        }

        user.setRoles(roleSet);

        userRepository.save(user);

        return "Success";
    }


}
