package com.akromo.controller;


import com.akromo.models.Role;
import com.akromo.models.User;
import com.akromo.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/index";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user",new User());
        return "admin/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        if (role.equals("Admin")) {
            user.getRoles().add(new Role("ROLE_ADMIN"));
        }
        user.getRoles().add(new Role("ROLE_USER"));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        if (role.equals("Admin")) {
            user.getRoles().add(new Role("ROLE_ADMIN"));
        }
        user.getRoles().add(new Role("ROLE_USER"));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

    @ModelAttribute("CurrentUser")
    public User addUser(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        return user;
    }
}
