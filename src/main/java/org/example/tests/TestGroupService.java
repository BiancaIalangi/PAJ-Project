package org.example.tests;

import org.example.domain.Group;
import org.example.exceptions.UnityvilleException;
import org.example.factory.UserFactory;
import org.example.service.GroupService;
import org.example.user.AdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestGroupService {
    private GroupService groupService;
    private AdminUser adminUser;

    @BeforeEach
    void setUp() {
        groupService = new GroupService();
        adminUser = (AdminUser) UserFactory.createNewUser("MANAGER", "admin@example.com");
    }

    @Test
    void testGetAllGroups() throws UnityvilleException {
        Set<Group> groups = groupService.getAllGroups();
        assertNotNull(groups);
        assertTrue(groups.isEmpty());

        groupService.createGroup("Group1", adminUser);
        Set<Group> allGroups = groupService.getAllGroups();
        assertEquals(1, allGroups.size());
    }

    @Test
    void testFindByName() throws UnityvilleException {
        groupService.createGroup("Group1", adminUser);
        Group foundGroup = groupService.findByName("Group1");
        assertNotNull(foundGroup);
        assertEquals("Group1", foundGroup.getName());

        UnityvilleException exception = assertThrows(UnityvilleException.class,
                () -> groupService.findByName("NonExistentGroup"));
        assertEquals("Group with name NonExistentGroup does not exists", exception.getMessage());
    }

    @Test
    void testCreateGroup() throws UnityvilleException {
        Group group = groupService.createGroup("Group1", adminUser);
        assertNotNull(group);
        assertEquals("Group1", group.getName());
        assertEquals(adminUser, group.getAdmin());

        UnityvilleException exception = assertThrows(UnityvilleException.class,
                () -> groupService.createGroup("Group1", adminUser));
        assertEquals("Group with name Group1 already exists", exception.getMessage());
    }
}
