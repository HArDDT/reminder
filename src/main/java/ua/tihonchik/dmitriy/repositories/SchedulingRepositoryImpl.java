package ua.tihonchik.dmitriy.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;
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
                "where events.activeevent and events.eventdate <= CURRENT_TIMESTAMP " +
                "order by events.userid;";

        SqlRowSet sqlRowSet = template.queryForRowSet(query);

        return createMap(sqlRowSet);

    }

    private Map<SimplifiedUser, List<Event>> createMap(SqlRowSet sqlRowSet){

        Map<SimplifiedUser, List<Event>> data = new HashMap<>();

        while (sqlRowSet.next()){
            Optional<SimplifiedUser> optionalUser = data.keySet().stream()
                    .filter(simplifiedUser -> Objects.equals(simplifiedUser.getId(), sqlRowSet.getString("userid")))
                    .findFirst();
            if (optionalUser.isEmpty()){
                ArrayList<Event> events = new ArrayList<>();
                events.add(createEvent(sqlRowSet));
                SimplifiedUser user = createUser(sqlRowSet);
                data.put(user, events);
            }
            else {
                SimplifiedUser simplifiedUser = optionalUser.get();
                List<Event> events = data.getOrDefault(simplifiedUser, new ArrayList<>());
                if(events.isEmpty()){
                    events.add(createEvent(sqlRowSet));
                }
                else {
                    if(events.stream().filter(event -> event.getId().equals(sqlRowSet.getString("id"))).findFirst().isEmpty()){
                        events.add(createEvent(sqlRowSet));
                    }
                }
            }

        }

        return data;

    }

    private SimplifiedUser createUser(SqlRowSet sqlRowSet){
        return new SimplifiedUser(sqlRowSet.getString("userid"), sqlRowSet.getString("email"), sqlRowSet.getString("name"));
    }

    private Event createEvent(SqlRowSet sqlRowSet){
        return new EventImpl(
                sqlRowSet.getString("eventid"),
                sqlRowSet.getString("userid"),
                sqlRowSet.getString("description"),
                String.valueOf(sqlRowSet.getTimestamp("eventdate")),
                true,
                sqlRowSet.getString("reminderexpression")
        );
    }

}
