package ua.tihiy.reminder.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.tihiy.reminder.users.User;
import ua.tihiy.reminder.users.UserCreationException;
import ua.tihiy.reminder.users.UserService;

import javax.sql.DataSource;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsersTests extends AbstractTestNGSpringContextTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService service;

    @BeforeMethod
    public void setUpData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/db/init.sql"));
        populator.addScript(new ClassPathResource("/db/data.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Test
    public void getAllUsers() {
        Assert.assertEquals(service.getUsers().size(), 4);
    }

    @Test
    public void getUserById() {
        final int actualValue = 3;
        Optional<User> userById = service.getUserById(actualValue);
        Assert.assertFalse(userById.isEmpty());
        int id = userById.get().getId();
        Assert.assertEquals(actualValue, id);
    }

    @Test
    public void getUserByInvalidId() {
        final int actualValue = 100;
        Optional<User> userById = service.getUserById(actualValue);
        Assert.assertTrue(userById.isEmpty());
    }

    @Test
    public void getUserByEmail() {
        final String actualValue = "alex@gmail.com";
        Optional<User> userByEmail = service.getUserByEmail(actualValue);
        Assert.assertFalse(userByEmail.isEmpty());
        String email = userByEmail.get().getEmail();
        Assert.assertEquals(actualValue, email);
    }

    @Test
    public void getUserByInvalidEmail() {
        final String invalidEmail = "";
        Optional<User> userByEmail = service.getUserByEmail(invalidEmail);
        Assert.assertTrue(userByEmail.isEmpty());
    }

    @Test
    public void deleteUser() {
        final int actualValue = 3;
        service.deleteUser(actualValue);
        Assert.assertEquals(service.getUsers().size(), 3);
    }

    @Test(expectedExceptions = {UsernameNotFoundException.class})
    public void deleteUserByInvalidId() {
        final int actualValue = 100;
        service.deleteUser(actualValue);
    }


    @Test
    public void createUser() {
        User user = new User("test.user@gmail.com", "123", "test user");
        int id = service.createUser(user);
        Optional<User> userById = service.getUserById(id);
        Assert.assertFalse(userById.isEmpty());
        Assert.assertEquals(id, userById.get().getId());
    }

    @Test(expectedExceptions = {UserCreationException.class})
    public void userIsExist() {
        User user = new User("mike@ukr.net", "123", "mike");
        service.createUser(user);
    }

}
