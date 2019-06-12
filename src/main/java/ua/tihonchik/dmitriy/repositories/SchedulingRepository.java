package ua.tihonchik.dmitriy.repositories;

import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;

import java.util.List;
import java.util.Map;

public interface SchedulingRepository {

    Map<SimplifiedUser, List<Event>> getData();

}
