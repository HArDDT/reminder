package ua.tihonchik.dmitriy.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;

import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;

    public EventRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int createEvent(Event event) {

        String sqlQuery = "INSERT INTO public.events(userid, description, eventdate, activeevent, reminderexpression)" +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Object[] eventFields = List.of(event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression()).toArray();

        return template.queryForObject(sqlQuery, Integer.class, eventFields);

    }

}
