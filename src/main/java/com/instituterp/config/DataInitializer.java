package com.instituterp.config;

import com.instituterp.entity.Permission;
import com.instituterp.entity.Role;
import com.instituterp.repository.PermissionRepository;
import com.instituterp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if not already present
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            log.info("Initializing default roles and permissions");
            initializeRolesAndPermissions();
        }
    }

    private void initializeRolesAndPermissions() {
        // Create permissions
        Permission[] permissions = {
                new Permission(null, "VIEW_DASHBOARD", "View dashboard", null, null),
                new Permission(null, "MANAGE_USERS", "Manage users", null, null),
                new Permission(null, "MANAGE_STUDENTS", "Manage students", null, null),
                new Permission(null, "MANAGE_COURSES", "Manage courses", null, null),
                new Permission(null, "MANAGE_BATCHES", "Manage batches", null, null),
                new Permission(null, "MANAGE_DEPARTMENTS", "Manage departments", null, null),
                new Permission(null, "MANAGE_ATTENDANCE", "Manage attendance", null, null),
                new Permission(null, "VIEW_REPORTS", "View reports", null, null),
        };

        for (Permission permission : permissions) {
            if (permissionRepository.findByName(permission.getName()).isEmpty()) {
                permissionRepository.save(permission);
            }
        }

        // Create ADMIN role
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator role with full access");
            adminRole.setPermissions(new HashSet<>(permissionRepository.findAll()));
            roleRepository.save(adminRole);
            log.info("ADMIN role created");
        }

        // Create USER role
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("Default user role");
            Set<Permission> userPermissions = new HashSet<>();
            userPermissions.add(permissionRepository.findByName("VIEW_DASHBOARD").orElseThrow());
            userRole.setPermissions(userPermissions);
            roleRepository.save(userRole);
            log.info("USER role created");
        }

        // Create FACULTY role
        if (roleRepository.findByName("FACULTY").isEmpty()) {
            Role facultyRole = new Role();
            facultyRole.setName("FACULTY");
            facultyRole.setDescription("Faculty role");
            Set<Permission> facultyPermissions = new HashSet<>();
            facultyPermissions.add(permissionRepository.findByName("VIEW_DASHBOARD").orElseThrow());
            facultyPermissions.add(permissionRepository.findByName("MANAGE_ATTENDANCE").orElseThrow());
            facultyPermissions.add(permissionRepository.findByName("VIEW_REPORTS").orElseThrow());
            facultyRole.setPermissions(facultyPermissions);
            roleRepository.save(facultyRole);
            log.info("FACULTY role created");
        }

        // Create STUDENT role
        if (roleRepository.findByName("STUDENT").isEmpty()) {
            Role studentRole = new Role();
            studentRole.setName("STUDENT");
            studentRole.setDescription("Student role");
            Set<Permission> studentPermissions = new HashSet<>();
            studentPermissions.add(permissionRepository.findByName("VIEW_DASHBOARD").orElseThrow());
            studentRole.setPermissions(studentPermissions);
            roleRepository.save(studentRole);
            log.info("STUDENT role created");
        }
    }
}
