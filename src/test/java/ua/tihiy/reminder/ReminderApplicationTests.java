package ua.tihiy.reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.tihiy.reminder.users.UserService;

import javax.sql.DataSource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReminderApplicationTests extends AbstractTestNGSpringContextTests {

    @Autowired
    UserService service;

    @Autowired
    DataSource dataSource;

    @BeforeClass
    public void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/db/init.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Test
    public void tableUsersIsEmpty() {
        Assert.assertEquals(service.getUsers().size(), 0);
    }

}
