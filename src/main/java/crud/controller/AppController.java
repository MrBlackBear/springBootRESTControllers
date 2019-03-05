package crud.controller;

import crud.model.User;
import crud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import crud.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
public class AppController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    /* to save an user*/
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    /*get all users*/
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userService.listUsers();
    }


    /*get user by userId*/
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId){
       User user=userService.getUserById(userId);
        if(user==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    /*update an user by userId*/
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable(value="id") Long userId,@Valid @RequestBody User userDetails){
        User user = userService.getUserById(userId);
        if(user==null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(userDetails.getName());
        user.setRoles(userDetails.getRoles());
        user.setPassword(userDetails.getPassword());
        user.setLogin(userDetails.getLogin());
        userService.updateUser(user);
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    /*Delete an user*/
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value="id") Long userId){
        User user = userService.getUserById(userId);
        if(user==null) {
            return ResponseEntity.notFound().build();
        }
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }
}