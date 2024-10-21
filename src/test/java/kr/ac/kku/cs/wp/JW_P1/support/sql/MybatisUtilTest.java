package kr.ac.kku.cs.wp.JW_P1.support.sql;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MybatisUtilTest {
    @Test
    public void testGetSession() {
        try (SqlSession session = MybatisUtil.getSession()) {
            assertNotNull(session);
        }
    }
}
