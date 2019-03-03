package crud.dao;

import crud.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleById(long id);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(long id);

    List<Role> getRoles();
}