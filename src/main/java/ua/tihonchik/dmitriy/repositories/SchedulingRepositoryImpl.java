package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class SchedulingRepositoryImpl implements SchedulingRepository {

    private JdbcTemplate template;
    private Logger logger = LoggerFactory.getLogger(SchedulingRepositoryImpl.class);

    public SchedulingRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Map<SimplifiedUser, List<Event>> getData() {

        String query = "select events.userid, users.name, users.email, events.id as eventid, events.description, events.eventdate, events.reminderexpression " +
                "from public.events as events left join public.users as users on events.userid = users.id " +
                "where events.activeevent " +
                "order by events.userid;";

        SqlRowSet sqlRowSet = template.queryForRowSet(query);

        return createMap(sqlRowSet);

    }

    private Map<SimplifiedUser, List<Event>> createMap(SqlRowSet sqlRowSet) {

        Map<SimplifiedUser, List<Event>> data = new HashMap<>();

        while (sqlRowSet.next()) {
            Optional<SimplifiedUser> optionalUser = findKey(sqlRowSet, data);

            if (optionalUser.isEmpty()) {
                putIfEmpty(sqlRowSet, data);
            } else {
                putIfPresent(sqlRowSet, data, optionalUser);
            }
        }

        return data;

    }

    private Optional<SimplifiedUser> findKey(SqlRowSet sqlRowSet, Map<SimplifiedUser, List<Event>> data) {
        return data.keySet().stream()
                .filter(simplifiedUser -> Objects.equals(simplifiedUser.getId(), sqlRowSet.getString("userid")))
                .findFirst();
    }

    private void putIfPresent(SqlRowSet sqlRowSet, Map<SimplifiedUser, List<Event>> data, Optional<SimplifiedUser> optionalUser) {
        SimplifiedUser simplifiedUser = optionalUser.get();
        List<Event> events = data.getOrDefault(simplifiedUser, new ArrayList<>());
        if (events.isEmpty()) {
            events.add(createEvent(sqlRowSet));
        } else {
            if (events.stream().filter(event -> event.getId() == (sqlRowSet.getInt("eventid"))).findFirst().isEmpty()) {
                events.add(createEvent(sqlRowSet));
            }
        }
    }

    private void putIfEmpty(SqlRowSet sqlRowSet, Map<SimplifiedUser, List<Event>> data) {
        ArrayList<Event> events = new ArrayList<>();
        events.add(createEvent(sqlRowSet));
        SimplifiedUser user = createUser(sqlRowSet);
        data.put(user, events);
    }

    private SimplifiedUser createUser(SqlRowSet sqlRowSet) {
        return new SimplifiedUser(sqlRowSet.getInt("userid"), sqlRowSet.getString("email"), sqlRowSet.getString("name"));
    }

    private Event createEvent(SqlRowSet sqlRowSet) {
        return new Event(
                sqlRowSet.getInt("eventid"),
                sqlRowSet.getInt("userid"),
                sqlRowSet.getString("description"),
                sqlRowSet.getTimestamp("eventdate").toLocalDateTime(),
                true,
                sqlRowSet.getString("reminderexpression")
        );
    }

}
