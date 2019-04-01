package crud.service.impl;

import crud.dao.UserDao;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        User user = userDao.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return user;
    }
}