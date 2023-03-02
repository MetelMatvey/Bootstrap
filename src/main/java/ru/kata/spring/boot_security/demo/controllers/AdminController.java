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
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RegAdminService;
import ru.kata.spring.boot_security.demo.service.RegUserService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.CustomValid;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CustomValid customValid;
    private final RegUserService regService;
    private final RegAdminService regAdminService;
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(CustomValid customValid, RegUserService regService, RegAdminService regAdminService,
                           UserService userService, RoleService roleService) {
        this.customValid = customValid;
        this.regService = regService;
        this.regAdminService = regAdminService;
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping
    public String getAll(Model model, Principal principal) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("existUser", userService.getUserByName(principal.getName()));
        model.addAttribute("roleList", roleService.getRoleList());
        return "admin/all";
    }


    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "roleNameList") List<String> roleNameList) {
        customValid.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/all";
        }
        regService.register(user, roleNameList);
        return "redirect:/admin";
    }



    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("userEd") User user,@PathVariable("id") long id,
                             @RequestParam(value = "roleNameListEdit") List<String> roleNameList) {
        for (String name : roleNameList) {
            user.setRole(roleService.showRole(name));
        }
        userService.update(id,user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
