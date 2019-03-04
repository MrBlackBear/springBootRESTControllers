package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    void updateUser(User user);

    void removeUser(long id);

    User getUserById(long id);

    List<User> listUsers();

    User getUserByUserName(String userName);
}