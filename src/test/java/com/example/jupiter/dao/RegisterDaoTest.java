package com.example.jupiter.dao;

import com.example.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RegisterDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private RegisterDao registerDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void register_ShouldReturnTrue_WhenUserIsSuccessfullyRegistered() {
        User user = new User(); // Fill the user object as needed
        when(session.getTransaction()).thenReturn(transaction);

        assertTrue(registerDao.register(user));

        verify(session).save(user);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void register_ShouldReturnFalse_WhenRegistrationFails() {
        User user = new User(); // Fill the user object as needed
        when(session.getTransaction()).thenReturn(transaction);
        doThrow(PersistenceException.class).when(session).save(user);

        assertFalse(registerDao.register(user));

        verify(session).save(user);
        verify(transaction).rollback();
        verify(session).close();
    }
}


/*
package com.example.jupiter.dao;

import com.example.jupiter.entity.db.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/*
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        // 指定jupiter-servlet.xml的绝对路径
        "file:/Users/eric/Desktop/Twitch/jupiter/src/main/webapp/WEB-INF/jupiter-servlet.xml"
})
public class RegisterDaoTest {
    // hold

    @Autowired
    RegisterDao registerDao;
  //  @Autowired
    LoginDao loginDao;
    @Test
    public void testRegister() {
        User testUser = new User();
        testUser.setUserId("XiaoHui");
        testUser.setPassword("123");
        testUser.setFirstName("Xiaohui");
        testUser.setLastName("Hope");
        registerDao.register(testUser);
        String firstName = loginDao.verifyLogin("XiaoHui", "123");
        assertEquals("XiaoHui", firstName);

    }


}


 */