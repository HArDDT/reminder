package ua.tihiy.reminder.scheduling;

import ua.tihiy.reminder.users.SimplifiedUser;
import ua.tihiy.reminder.events.repeatable.RepeatableEvent;

import java.util.List;
import java.util.Map;

public interface SchedulingRepository {

    Map<SimplifiedUser, List<RepeatableEvent>> getData();

}
