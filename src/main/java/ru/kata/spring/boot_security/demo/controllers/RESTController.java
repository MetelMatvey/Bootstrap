package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.Exceptions.ExceptionData;
import ru.kata.spring.boot_security.demo.Exceptions.NoSuchUserEx;
import ru.kata.spring.boot_security.demo.Exceptions.UserNameEx;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RESTController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public RESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ExceptionData> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String erMessage = getErFromBR(bindingResult);
            return new ResponseEntity<>(new ExceptionData(erMessage), HttpStatus.BAD_REQUEST);
        } try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNameEx e) {
            throw new UserNameEx("User with such username already exists");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new NoSuchUserEx(String.format("User with id = %l doesnt exist", id));
        }
        return new ResponseEntity<User>(userService.getById(id),HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ExceptionData> pageDelete(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new NoSuchUserEx(String.format("User with id = %l doesnt exist", id));
        } userService.deleteById(id);
        return new ResponseEntity<>(new ExceptionData("User deleted"), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    public String getErFromBR(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleException(NoSuchUserEx exception) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setInfo(exceptionData.getInfo());
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }
}
