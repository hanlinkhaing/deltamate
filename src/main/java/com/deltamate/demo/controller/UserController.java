package com.deltamate.demo.controller;

import com.deltamate.demo.model.Role;
import com.deltamate.demo.model.User;
import com.deltamate.demo.service.RoleService;
import com.deltamate.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String home(Model model) {
        return "view/home";
    }

    @GetMapping("users/addUser")
    public String addUser(Model model) {
        List<Role> roles = new ArrayList<>();
        User user = new User();
        user.setRoles(roles);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", user);
        return "view/addUser";
    }

    @PostMapping("users/addUser")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            return "view/addUser";
        }

        userService.createUser(user);
        redirectAttributes.addFlashAttribute("users", userService.viewAllUser());
        return "redirect:/users";
    }

    @GetMapping("users")
    public String viewAllUser(Model model) {
        model.addAttribute("users", userService.viewAllUser());
        return "view/viewAllUser";
    }

    @GetMapping("users/editUser/{id}")
    public String editUser(@PathVariable("id") String id, Model model){
        User user = userService.findUserByID(id);
        user.setConfirmPassword(user.getPassword());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", user);
        return "view/editUser";
    }

    @PostMapping("users/editUser")
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            boolean idError = false;
            for (ObjectError error: errors) {
                if (("userID").equals(error.getObjectName())){
                    idError = true;
                }
            }
            if(errors.size() > 1 && !idError) return "view/editUser";
        }

        userService.editUser(user);
        redirectAttributes.addFlashAttribute("users", userService.viewAllUser());
        return "redirect:/users";
    }

    @GetMapping("users/removeUser/{id}")
    public String removeUser(@PathVariable("id")String id, RedirectAttributes redirectAttributes) {
        userService.removeUser(id);
        redirectAttributes.addFlashAttribute("users", userService.viewAllUser());
        return "redirect:/users";
    }

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }
}
