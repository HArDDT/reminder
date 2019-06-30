package ua.tihiy.reminder.scheduling;

import ua.tihiy.reminder.events.repeatable.RepeatableEvent;
import ua.tihiy.reminder.users.SimplifiedUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SchedulingService {

    LocalDate getNextValidDate(String expression, LocalDateTime eventDate);

    Map<SimplifiedUser, List<RepeatableEvent>> getData();

    Map<String, String> emailsWithMessage();

    void sendEmails();
}
