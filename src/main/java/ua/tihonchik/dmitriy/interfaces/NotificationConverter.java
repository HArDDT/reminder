package ua.tihonchik.dmitriy.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface NotificationConverter {

    LocalDate getNextValidDate(String expression, LocalDateTime eventDate);

}
