package ua.tihiy.reminder.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.tihiy.reminder.events.EventCreationException;
import ua.tihiy.reminder.events.repeatable.RepeatableEvent;
import ua.tihiy.reminder.events.repeatable.RepeatableEventService;

import javax.sql.DataSource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MultiEventsTests extends AbstractTestNGSpringContextTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    RepeatableEventService service;

    @BeforeMethod
    public void setUpData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/db/init.sql"));
        populator.addScript(new ClassPathResource("/db/data.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Test(groups = {"MultiEventsTests"})
    public void getRepeatableEventsByUserId() {
        final int sizeInTestDatabase = service.getEvents(1).size();
        Assert.assertEquals(sizeInTestDatabase, 6);
    }

    @Test(groups = {"MultiEventsTests"})
    public void getRepeatableEventsByUserIdAndEventId() {
        final RepeatableEvent eventFromDb = service.getEvent(1);
        Assert.assertEquals(eventFromDb.getId(), 1);
    }

    @Test(groups = {"MultiEventsTests"})
    public void createRepeatableEventsByUserId() {
        RepeatableEvent event = new RepeatableEvent(2, "test description", "Fri, 14 Feb 2020 15:01:26 GMT", true, "3, day");
        int id = service.createEvent(event);
        RepeatableEvent eventById = service.getEvent(id);
        Assert.assertEquals(id, eventById.getId());
        Assert.assertEquals("test description", eventById.getDescription());
    }

    @Test(groups = {"MultiEventsTests"}, expectedExceptions = EventCreationException.class)
    public void createRepeatableEventsWithInvalidUserId() {
        RepeatableEvent event = new RepeatableEvent(100, "test description", "Fri, 14 Feb 2020 15:01:26 GMT", true, "3, day");
        service.createEvent(event);
    }

}
