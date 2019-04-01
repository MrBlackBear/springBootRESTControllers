package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import crud.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AppRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AppRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("user")
    public User createUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return this.userService.listUsers();
    }

    @GetMapping("roles")
    public List<Role> getAllRoles() {
        return this.roleService.getRoles();
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @CachePut
    @PutMapping("users/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(userDetails.getName());
        user.setRoles(userDetails.getRoles());
        user.setPassword(userDetails.getPassword());
        user.setLogin(userDetails.getLogin());
        userService.updateUser(user);
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }
}