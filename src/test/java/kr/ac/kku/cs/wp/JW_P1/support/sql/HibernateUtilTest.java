package kr.ac.kku.cs.wp.JW_P1.support.sql;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateUtilTest {
    @Test
    public void testGetSession() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            assertNotNull(session);
        }
    }
}
