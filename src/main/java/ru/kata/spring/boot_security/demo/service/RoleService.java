package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);

    List<Role> getAllRoles();

    @Transactional
    Role getRoleByName(String name);

    @Transactional
    void saveRole(Role role);

    @Transactional
    void deleteRole(Long id);
}
