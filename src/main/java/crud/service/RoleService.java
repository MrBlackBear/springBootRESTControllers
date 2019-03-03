package crud.service;

import crud.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(long id);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(long id);

    List<Role> getRoles();
}
