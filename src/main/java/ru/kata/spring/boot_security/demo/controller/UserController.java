package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAnyAuthority()")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping(value = "new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String newUser (User user) {
        return "/new";
    }

    @PostMapping(value = "/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveNewUser (User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String findUser(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PutMapping(value = "/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@PathVariable("id") Long id, User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/edit/{id}";
        }

        userService.saveUser(user);
        return "redirect:/";
    }


    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/login")
    @PreAuthorize("hasAnyAuthority()")
    public String getLoginPage(){
        return "/login";
    }

}
