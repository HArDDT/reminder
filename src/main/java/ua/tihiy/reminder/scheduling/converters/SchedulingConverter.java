package ua.tihiy.reminder.scheduling.converters;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SchedulingConverter {

    LocalDate getNextValidDate(String expression, LocalDateTime eventDate);

}
