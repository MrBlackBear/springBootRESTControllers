package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import crud.service.UserService;
import org.springframework.web.servlet.ModelAndView;


import java.util.Collections;
import java.util.List;
import java.util.Set;


@RestController
public class AppController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(String error, String logout, ModelAndView modelAndView) {
        if (error != null) {
            modelAndView.addObject("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            modelAndView.addObject("message", "Logged out successfully.");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/admin")
    public ModelAndView startPageAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(new User());
        modelAndView.addObject("users", this.userService.listUsers());
        modelAndView.addObject("roles", this.roleService.getRoles());
        modelAndView.setViewName("admin_menu");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute User user, @RequestParam Long roleId) {
        Set<Role> roleSet = Collections.singleton(roleService.getRoleById(roleId));
        user.setRoles(roleSet);
        userService.addUser(user);

        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/delete")
    public ModelAndView deleteEmployee(@RequestParam Long id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ModelAndView updatePost(@ModelAttribute("admin/user") User user, @RequestParam Long roleId) {
        Set<Role> roleSet = Collections.singleton(roleService.getRoleById(roleId));
        user.setRoles(roleSet);
        userService.updateUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value = "/admin/update")
    public ModelAndView updateGet(@RequestParam Long id, ModelAndView model) {
        model.setViewName("update");
        User user = userService.getUserById(id);
        model.addObject("user", user);
        List<Role> roles = roleService.getRoles();
        model.addObject("roles", roles);
        return model;
    }

    @RequestMapping(value = "/user")
    public ModelAndView helloPageUser(ModelAndView modelAndView) {
        modelAndView.setViewName("user_hello");
        return modelAndView;
    }

    @RequestMapping(value = "/error")
    public ModelAndView error(ModelAndView modelAndView) {
        modelAndView.setViewName("error");
        return modelAndView;
    }
}