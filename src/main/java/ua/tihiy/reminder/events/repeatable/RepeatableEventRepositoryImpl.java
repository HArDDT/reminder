package ua.tihiy.reminder.events.repeatable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ua.tihiy.reminder.events.EventCreationException;
import ua.tihiy.reminder.events.EventDeleteException;
import ua.tihiy.reminder.events.EventNotFoundException;

import java.sql.Types;
import java.util.Collection;

@Repository
@PropertySource("classpath:properties/sql/queries.properties")
public class RepeatableEventRepositoryImpl implements RepeatableEventRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(RepeatableEventRepositoryImpl.class);
    private Environment environment;

    public RepeatableEventRepositoryImpl(JdbcTemplate template, Environment environment) {
        this.template = template;
        this.environment = environment;
    }

    @Override
    public int createEvent(RepeatableEvent repeatableEvent) {

        String sqlQuery = environment.getProperty("event.create");

        Object[] eventFields = {
                repeatableEvent.getUserId(),
                repeatableEvent.getDescription(),
                repeatableEvent.getEventDate(),
                repeatableEvent.isActiveEvent(),
                repeatableEvent.getReminderExpression(),
                repeatableEvent.getUserId()};

        SqlRowSet rowSet = template.queryForRowSet(sqlQuery, eventFields);

        if (rowSet.next()) {
            return rowSet.getInt("id");
        } else {
            String errorMessage = "User with id - " + repeatableEvent.getUserId() + " not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public Collection<RepeatableEvent> getEvents(int userId) {

        String sqlQuery = environment.getProperty("event.get.all.by.userid");

        return template.query(sqlQuery, new Object[]{userId}, new RepeatableEventRowMapper());

    }

    @Override
    public RepeatableEvent getEvent(int eventId) {

        String sqlQuery = environment.getProperty("event.get.by.id");
        SqlRowSet rowSet = template.queryForRowSet(sqlQuery, eventId);
        if (rowSet.next()) {
            return RepeatableEventRowMapper.getEvent(rowSet);
        } else {
            String errorMessage = "Event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventNotFoundException(errorMessage);
        }

    }

    @Override
    public void updateEvent(RepeatableEvent repeatableEvent) {

        String sqlQuery = environment.getProperty("event.update");

        Object[] eventFields = {repeatableEvent.getDescription(),
                repeatableEvent.getEventDate(),
                repeatableEvent.isActiveEvent(),
                repeatableEvent.getReminderExpression(),
                repeatableEvent.getId()};

        int[] argTypes = {Types.VARCHAR, Types.TIMESTAMP, Types.BOOLEAN, Types.VARCHAR, Types.INTEGER};

        int countOfRow;

        try {
            countOfRow = template.update(sqlQuery, eventFields, argTypes);
        } catch (Exception ex) {
            String errorMessage = "Incorrect data!";
            logger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        if (countOfRow == 0) {
            String errorMessage = "Event with id - " + repeatableEvent.getId() + ", not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public void deleteEvent(int eventId) {

        String sqlQuery = environment.getProperty("event.delete");

        int countOfRow = template.update(sqlQuery, new Object[]{eventId}, new int[]{Types.INTEGER});

        if (countOfRow == 0) {
            String errorMessage = "Event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventDeleteException(errorMessage);
        }

    }
}
