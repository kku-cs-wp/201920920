// UserDAO.java
package kr.ac.kku.cs.wp.JW_P1.user.dao;

import kr.ac.kku.cs.wp.JW_P1.user.entity.User;

import java.util.List;

public interface UserDAO {
    User getUserById(String userId);
    List<User> getUsers(User user);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
