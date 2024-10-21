package kr.ac.kku.cs.wp.JW_P1.user.dao;

import kr.ac.kku.cs.wp.JW_P1.user.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDAOHibernateImplTest {

    @Test
    public void testGetUserById() {
        UserDAO userDao = new UserDAOHibernateImpl();
        String userId = "kku_1001";
        User user = userDao.getUserById(userId);
        assertNotNull(user);
    }
}