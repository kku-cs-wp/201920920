// UserServiceImpl.java
package kr.ac.kku.cs.wp.JW_P1.user.service;

import kr.ac.kku.cs.wp.JW_P1.user.dao.UserDAO;
import kr.ac.kku.cs.wp.JW_P1.user.dao.UserDAOHibernateImpl;
import kr.ac.kku.cs.wp.JW_P1.user.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDao = new UserDAOHibernateImpl();

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public List<User> getUsers(User user) {
        return userDao.getUsers(user);
    }

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
