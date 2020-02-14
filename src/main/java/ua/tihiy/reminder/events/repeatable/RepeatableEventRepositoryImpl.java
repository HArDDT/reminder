package ua.tihiy.reminder.events.repeatable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ua.tihiy.reminder.events.EventCreationException;
import ua.tihiy.reminder.events.EventDeleteException;
import ua.tihiy.reminder.events.EventNotFoundException;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sqlQuery, new String[]{"id"});
                ps.setInt(1, repeatableEvent.getUserId());
                ps.setString(2, repeatableEvent.getDescription());
                ps.setTimestamp(3, Timestamp.valueOf(repeatableEvent.getEventDate()));
                ps.setBoolean(4, repeatableEvent.isActiveEvent());
                ps.setString(5, repeatableEvent.getReminderExpression());
                return ps;
            }, keyHolder);

            return (int) keyHolder.getKey();

        } catch (DataIntegrityViolationException exception) {
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
