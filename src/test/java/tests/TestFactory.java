package tests;

import org.example.factory.UserFactory;
import org.example.user.AdminUser;
import org.example.user.BasicUser;
import org.example.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestFactory {
    @Test
    void testCreatAdminAccount() {
        String name = "First User";
        String username = "FirstUser";
        String password = "918000";
        String email = "firstuser@mail.com";
        User admin = UserFactory.createNewUser("manager", name);

        assertTrue(admin instanceof AdminUser);
        assertEquals(username, ((AdminUser) admin).getUsername());
        assertEquals(name, ((AdminUser) admin).getName());
        assertEquals(password, ((AdminUser) admin).getPassword());
        assertEquals(email, ((AdminUser) admin).getEmail());
    }

    @Test
    void testCreatBasicAccount() {
        String name = "Second User";
        String username = "SecondUser";
        String password = "1019000";
        String email = "seconduser@mail.com";
        User user = UserFactory.createNewUser("programmer", name);

        assertTrue(user instanceof BasicUser);
        assertEquals(name, ((BasicUser) user).getName());
        assertEquals(username, ((BasicUser) user).getUsername());
        assertEquals(password, ((BasicUser) user).getPassword());
        assertEquals(email, ((BasicUser) user).getEmail());
    }


    @Test
    void testCreatNoAccount() {
        String name = "Third User";
        String password = "password3";
        User user = UserFactory.createNewUser(null, name);

        assertNull(user);
    }
}
