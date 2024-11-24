package com.sanedge.ecommerce_midtrans.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.enums.ERole;
import com.sanedge.ecommerce_midtrans.models.Role;
import com.sanedge.ecommerce_midtrans.repository.RoleRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        this.seedRoles();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();

            admin.setName(ERole.ROLE_ADMIN);

            Role mod = new Role();

            mod.setName(ERole.ROLE_MODERATOR);

            Role user = new Role();
            user.setName(ERole.ROLE_USER);

            roleRepository.save(admin);
            roleRepository.save(mod);
            roleRepository.save(user);
        }

    }

}
