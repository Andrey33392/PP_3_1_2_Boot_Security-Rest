package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String users(Model model) {
        List<User> user = userService.findAll();
        model.addAttribute("users", user);

        return "admin";
    }

    @GetMapping("/user_id/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user1", userService.getById(id));

        return "admin";
    }

    @GetMapping("/user_id")
    public String pageUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        return "user_id";
    }
    @GetMapping("/edit/{id}")
    public String updateUserFrom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user1", userService.getById(id));
        model.addAttribute("listRoles", roleService.findAll());
        return "admin";
    }

    @PutMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user1") @Valid User user, @RequestParam("listRoles") List<Long> roles) {

        user.setRoles(roleService.getRolesByIds(roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/addUser")
    public String createUser(Model model) {
        model.addAttribute("userFrom", new User());
        return "admin";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("userFrom") @Valid User user, BindingResult bindingResult, @RequestParam("listRoles") List<Long> roles) {
//        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin";
        }

        user.setRoles(roleService.getRolesByIds(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
