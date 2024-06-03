package org.example.factory;

import org.example.user.AdminUser;
import org.example.user.BasicUser;
import org.example.user.User;

public class UserFactory {
    public static final String MANAGER = "manager";

    public static User createNewUser(String title, String name, String password, String email) {
        if (title == null) {
            return null;
        }

        String username = name.replace(" ", "");
        if (title.equalsIgnoreCase(MANAGER)) {
            return new AdminUser(name, MANAGER, username, password, email);
        }
        return new BasicUser(name, title.toLowerCase(), username, password, email);
    }
}
