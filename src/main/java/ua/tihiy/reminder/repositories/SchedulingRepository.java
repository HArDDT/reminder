package ua.tihiy.reminder.repositories;

import ua.tihiy.reminder.dto.SimplifiedUser;
import ua.tihiy.reminder.entities.Event;

import java.util.List;
import java.util.Map;

public interface SchedulingRepository {

    Map<SimplifiedUser, List<Event>> getData();

}
