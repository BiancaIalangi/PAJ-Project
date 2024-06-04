package org.example.factory;

import org.example.user.AdminUser;
import org.example.user.BasicUser;
import org.example.user.User;

import java.util.Locale;

public class UserFactory {
    public static final String MANAGER = "manager";

    public static User createNewUser(String title, String name) {
        if (title == null || "".equals(title)) {
            return null;
        }

        String username = name.replace(" ", "");
        String email = username.toLowerCase(Locale.ROOT) + "@mail.com";
        String password = String.valueOf(username.length()) + String.valueOf(email.length()) + "000";

        if (title.equalsIgnoreCase(MANAGER)) {
            return new AdminUser(name, MANAGER, username, password, email);
        }
        return new BasicUser(name, title.toLowerCase(), username, password, email);
    }
}
