package com.deltamate.demo.controller;

import com.deltamate.demo.model.Role;
import com.deltamate.demo.model.User;
import com.deltamate.demo.repository.RoleRepository;
import com.deltamate.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    private static final String ID = "DM001";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_OFFICE = "ROLE_OFFICE";
    private static final String ROLE_USER = "ROLE_USER";

    public DatabaseLoader(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args){
        Role adminRole = new Role(ROLE_ADMIN);
        if(roleRepository.findAll().size() <= 0) {
            Role officeRole = new Role(ROLE_OFFICE);
            Role userRole = new Role(ROLE_USER);
            roleRepository.saveAll(Arrays.asList(adminRole, officeRole, userRole));
        }
        User user = new User();
        user.setUserID(ID);
        user.setRoles(Arrays.asList(adminRole));
        user.setCompany("Delta Mate Myanmar");
        user.setPassword(bCryptPasswordEncoder.encode("DMAdmin"));
        user.setName("Main Admin");
        if (!userRepository.existsById(ID)) userRepository.save(user);
    }
}
