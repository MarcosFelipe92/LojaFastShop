package com.fastshop.e_commerce.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fastshop.e_commerce.models.RoleBO;
import com.fastshop.e_commerce.models.UserBO;
import com.fastshop.e_commerce.repositories.RoleRepository;
import com.fastshop.e_commerce.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Profile(value = "test")
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        RoleBO roleAdmin = roleRepository.findByName(RoleBO.getAdminRole()).get();

        UserBO userAdmin = userRepository.findByEmail("admin@gmail.com");

        if (userAdmin != null) {
            System.out.println("admin ja existe");
        } else {
            UserBO user = new UserBO();
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Set.of(roleAdmin));
            userRepository.save(user);
        }

    }
}