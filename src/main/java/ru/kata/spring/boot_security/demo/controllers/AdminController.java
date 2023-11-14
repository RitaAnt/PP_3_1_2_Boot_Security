package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserWithRolesDAO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody UserWithRolesDAO userWithRolesDAO) {
        // Переносим изменения из UserWithRolesDAO в сущность User
        User user = userWithRolesDAO.getUser();
        // Обновляем роли пользователя
        user.setRoles(userWithRolesDAO.getRoles()
                .stream()
                .filter(Role::isSelected)
                .collect(Collectors.toList()));

        userService.saveUser(user);

        return ResponseEntity.ok("User saved successfully");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserWithRolesDAO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            // Преобразуем пользователя и его роли в UserWithRolesDAO для передачи на фронт
            UserWithRolesDAO userWithRolesDAO = new UserWithRolesDAO();
            userWithRolesDAO.setUser(user);
            userWithRolesDAO.setRoles(userService.getAllRoles()); // Передаем все роли, чтобы админ мог выбрать

            // Устанавливаем флаг isSelected в true для ролей, которые у пользователя уже есть
            for (Role role : userWithRolesDAO.getRoles()) {
                role.setSelected(user.getRoles().contains(role));
            }

            return ResponseEntity.ok(userWithRolesDAO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
