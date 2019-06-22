package ua.tihiy.reminder.additional;

import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
public class NotificationConverterImpl implements NotificationConverter {

    /**
     * NotificationConverterImpl - return the next valid date of notification
     *
     * @param expression - the template should have the number of days before the event date and the frequency of recurrence (day, week, month, year).
     *                   Like this: "3week", "0, year", "2 day", "1 month".
     * @param eventDate  - real date of event
     *                   {@link NotificationConverterImpl#getNextValidDate(String, LocalDateTime)}
     */
    @Override
    public LocalDate getNextValidDate(String expression, LocalDateTime eventDate) {
        if (Objects.isNull(expression)) {
            throw new NullPointerException();
        }
        String lowerCaseExpression = expression.toLowerCase();
        checkExpression(lowerCaseExpression);

        String cronExpression = getCronExpression(eventDate, lowerCaseExpression);
        CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        LocalDate startDayForGenerator = getStartDayForGenerator(eventDate, lowerCaseExpression);
        Date firstNotificationDate = Date.from(startDayForGenerator.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nextRunDate = generator.next(firstNotificationDate);
        return nextRunDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void checkExpression(String expression) {
        if (expression.isBlank()) {
            throw new IllegalArgumentException("empty expression!");
        }
        if (!expression.matches("^\\d{1,3}\\s?([,/.])?\\s?(day|week|month|year)$")) {
            throw new IllegalArgumentException("The expression must contain:\n - the number of day and\n - the word duration! - \"day|week|month|year\"");
        }
    }

    private LocalDate getStartDayForGenerator(LocalDateTime eventDate, String expression) {
        LocalDate notificationDate = getTheFirstNotificationDate(eventDate, expression);
        LocalDate notificationDateExcluded = notificationDate.minusDays(1);
        if (LocalDate.now().isAfter(notificationDateExcluded)) {
            return LocalDate.now();
        }
        return notificationDateExcluded;
    }

    private String getCronExpression(LocalDateTime eventDate, String expression) {
        String duration = getStringDuration(expression);
        LocalDate firstNotificationDate = getTheFirstNotificationDate(eventDate, expression);
        String dayOfWeek = firstNotificationDate.getDayOfWeek().toString().substring(0, 3);
        String month = firstNotificationDate.getMonth().toString().substring(0, 3);
        int dayOfMonth = firstNotificationDate.getDayOfMonth();
        String regex = "";
        switch (duration) {
            case "day":
                regex = "59 59 12 * * ?";
                break;
            case "week":
                regex = "0 0 1 * * " + dayOfWeek;
                break;
            case "month":
                regex = "0 0 1 " + dayOfMonth + " * ?";
                break;
            case "year":
                regex = "0 0 1 " + dayOfMonth + " " + month + " ?";
        }

        return regex;
    }

    private LocalDate getTheFirstNotificationDate(LocalDateTime eventDate, String expression) {
        int countOfDays = getCountDayBeforeEvent(expression);
        return eventDate.minusDays(countOfDays).toLocalDate();
    }

    private String getStringDuration(String expression) {
        String clearedExpression = expression.replaceAll("(\\d|\\W)", "");
        if (clearedExpression.matches("day")) return "day";
        if (clearedExpression.matches("week")) return "week";
        if (clearedExpression.matches("month")) return "month";
        if (clearedExpression.matches("year")) return "year";
        return "";
    }

    private int getCountDayBeforeEvent(String expression) {
        return Integer.parseInt(expression.replaceAll("(\\D|\\W)", ""));
    }

}
