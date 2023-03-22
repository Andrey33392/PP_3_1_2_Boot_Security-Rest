package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller

public class TestController {

    private final UserService userService;
    private final RoleService roleService;

    public TestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showUser(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("testUsers", userService.findAll());

        return "index";

    }

    @GetMapping("/edit1/{id}")
    public String updateUserFrom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("test", userService.getById(id));
        model.addAttribute("listRoles", roleService.findAll());
        return "index";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@ModelAttribute("test") @Valid User user,
                             @RequestParam("listRoles") List<Long> roles) {
       user.setRoles(roleService.getRolesByIds(roles));
        userService.updateUser(user);
        return "redirect:/";
    }

}
