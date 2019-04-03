package crud.impl;

import crud.dao.UserDao;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao usersDAO;

    @Autowired
    public UserServiceImpl(UserDao usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    @Transactional
    public User addUser(User User) {
        this.usersDAO.addUser(User);
        return User;
    }

    @Override
    @Transactional
    public void updateUser(User User) {
        this.usersDAO.updateUser(User);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        this.usersDAO.removeUser(id);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return this.usersDAO.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.usersDAO.listUsers();
    }

    @Override
    public User getUserByUserName(String userName) {
        return usersDAO.getUserByUserName(userName);
    }
}