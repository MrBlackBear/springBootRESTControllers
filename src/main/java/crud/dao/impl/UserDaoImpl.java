package crud.dao.impl;

import crud.dao.UserDao;
import crud.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return  entityManager.createQuery("FROM User").getResultList();
    }

    @Override
    @Transactional
    public User getUserByUserName(String userName) {
        Query query = entityManager.createQuery("FROM User where login = :paramName");
        query.setParameter("paramName", userName);
        return (User) query.getSingleResult();
    }
}