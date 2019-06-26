package ua.tihiy.reminder.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ua.tihiy.reminder.entities.Event;
import ua.tihiy.reminder.entities.rowmappers.EventRowMapper;
import ua.tihiy.reminder.exceptions.EventCreationException;
import ua.tihiy.reminder.exceptions.EventDeleteException;
import ua.tihiy.reminder.exceptions.EventNotFoundException;

import java.sql.Types;
import java.util.Collection;

@Repository
@PropertySource("classpath:properties/sql/queries.properties")
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);
    private Environment environment;

    public EventRepositoryImpl(JdbcTemplate template, Environment environment) {
        this.template = template;
        this.environment = environment;
    }

    @Override
    public int createEvent(Event event) {

        String sqlQuery = environment.getProperty("event.create");

        Object[] eventFields = {
                event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression(),
                event.getUserId()};

        SqlRowSet rowSet = template.queryForRowSet(sqlQuery, eventFields);

        if (rowSet.next()) {
            return rowSet.getInt("id");
        } else {
            String errorMessage = "User with id - " + event.getUserId() + " not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public Collection<Event> getEvents(int userId) {

        String sqlQuery = environment.getProperty("event.get.all.by.userid");

        return template.query(sqlQuery, new Object[]{userId}, new EventRowMapper());

    }

    @Override
    public Event getEvent(int eventId) {

        String sqlQuery = environment.getProperty("event.get.by.id");
        SqlRowSet rowSet = template.queryForRowSet(sqlQuery, eventId);
        if (rowSet.next()) {
            return EventRowMapper.getEvent(rowSet);
        } else {
            String errorMessage = "Event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventNotFoundException(errorMessage);
        }

    }

    @Override
    public void updateEvent(Event event) {

        String sqlQuery = environment.getProperty("event.update");

        Object[] eventFields = {event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression(),
                event.getId()};

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
            String errorMessage = "Event with id - " + event.getId() + ", not found!";
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
