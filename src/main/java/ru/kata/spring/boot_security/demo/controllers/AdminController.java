package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.AdminService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/user")
    public String getUsers(Model model) {
        model.addAttribute("users", adminService.getUsers());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", adminService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("listRoles", roleService.getListRoles());
        return "new";
    }

    @PostMapping("/user")
    public String create(@ModelAttribute("user") User user) {
        adminService.addUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id, Model roles) {
//        roles.addAttribute("listRoles", roleService.getListRoles());
        model.addAttribute("user", adminService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        adminService.updateUser(id, user);
        return "redirect:/admin/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        adminService.deleteUser(id);
        return "redirect:/admin/user";
    }

}

