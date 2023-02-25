package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RegAdminService;
import ru.kata.spring.boot_security.demo.service.RegUserService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.CustomValid;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CustomValid customValid;
    private final RegUserService regService;
    private final RegAdminService regAdminService;
    private final UserService userService;

    public AdminController(CustomValid customValid, RegUserService regService, RegAdminService regAdminService, UserService userService) {
        this.customValid = customValid;
        this.regService = regService;
        this.regAdminService = regAdminService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationAdmin(@ModelAttribute(name = "admin") User user) {
        return "admin/registration";
    }

    @PostMapping("/registration")
    public String regAdmin(@ModelAttribute(name = "admin") @Valid User user, BindingResult bindingResult) {
        customValid.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/registration";
        }
        regAdminService.register(user);
        return "redirect:/login";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/all";
    }

    @GetMapping("/user/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/user";
    }

    @GetMapping("/new")
    public String saveUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/newUser";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        customValid.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }
        regService.register(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin/edit";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
