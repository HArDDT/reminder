package ua.tihiy.reminder.additional;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class NotificationService {

    private NotificationConverterFactory factory;

    public NotificationService(NotificationConverterFactory factory) {
        this.factory = factory;
    }

    /**
     * NotificationService - return the next valid date of notification
     *
     * @param expression - the template should have the number of days before the event date and the frequency of recurrence (day, week, month, year).
     *                   Like this: "3week", "0, year", "2 day", "1 month".
     * @param eventDate  - real date of event
     *                   {@link NotificationService#getNextValidDate(String, LocalDateTime)}
     */

    public LocalDate getNextValidDate(String expression, LocalDateTime eventDate){
        NotificationConverter converter = factory.getNotificationConverter(expression).
                orElseThrow(() -> new IllegalArgumentException("Incorrect expression: " + expression + "\n" +
                        "The template should have the number of days before the event date and the frequency of recurrence (day, week, month, year).\n" +
                        "Like this: \"3week\", \"0, year\", \"2 day\", \"1 month\""));
        return converter.getNextValidDate(expression, eventDate);
    }

}
