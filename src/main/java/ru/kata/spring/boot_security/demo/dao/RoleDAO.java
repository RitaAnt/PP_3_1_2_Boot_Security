package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role getRoleByName(String name);

    void saveRole(Role role);

    void deleteRole(Long id);
}
