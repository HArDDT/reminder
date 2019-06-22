package ua.tihiy.reminder.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ua.tihiy.reminder.additional.EventRowMapper;
import ua.tihiy.reminder.exceptions.EventCreationException;
import ua.tihiy.reminder.exceptions.EventDeleteException;
import ua.tihiy.reminder.exceptions.EventNotFoundException;
import ua.tihiy.reminder.entities.Event;

import java.sql.Types;
import java.util.Collection;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);

    public EventRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int createEvent(Event event) {

        String sqlQuery = "insert into public.events(userid, description, eventdate, activeevent, reminderexpression) " +
                "values (?, ?, ?, ?, ? )" +
                "RETURNING id";

        Object[] eventFields = {
                event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()};
        try {
            return template.queryForObject(sqlQuery, Integer.class, eventFields);
        } catch (Exception exception) {
            String errorMessage = "User with id - " + event.getUserId() + " not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage, exception);
        }

    }

    @Override
    public Collection<Event> getEvents(int userId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where userid = ?";

        return template.query(sqlQuery, new Object[] {userId}, new EventRowMapper());

    }

    @Override
    public Event getEvent(int eventId) {

        String sqlQuery = "select id, userid, description, eventdate, activeevent, reminderexpression from public.events where id = ?";

        try {
            return template.queryForObject(sqlQuery, new Object[] {eventId}, new EventRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            String errorMessage = "Event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventNotFoundException(errorMessage);
        }

    }

    @Override
    public void updateEvent(Event event) {

        String sqlQuery = "update public.events set description = ?, eventdate = ?, activeevent = ?, reminderexpression = ? where id = ?";

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
            logger.error("incorrect data!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect data!");
        }

        if (countOfRow == 0) {
            String errorMessage = "Event with id - " + event.getId() + ", not found!";
            logger.error(errorMessage);
            throw new EventCreationException(errorMessage);
        }

    }

    @Override
    public void deleteEvent(int eventId) {

        String sqlQuery = "delete from public.events where id = ?";

        int countOfRow = template.update(sqlQuery, new Object[] {eventId}, new int[] {Types.INTEGER});

        if (countOfRow == 0) {
            String errorMessage = "Event with id - " + eventId + " not found!";
            logger.error(errorMessage);
            throw new EventDeleteException(errorMessage);
        }

    }
}