package ua.tihonchik.dmitriy.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private JdbcTemplate template;

    public EventRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int createEvent(Event event) {
        return template.update("INSERT INTO public.events(userid, description, eventdate, activeevent, reminderexpression)" +
                        "VALUES (?, ?, ?, ?, ?)",
                event.getUserId(),
                event.getDescription(),
                event.getEventDate(),
                event.isActiveEvent(),
                event.getReminderExpression());
    }

}
