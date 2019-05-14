package ua.tihonchik.dmitriy.entities;

import org.springframework.scheduling.support.CronSequenceGenerator;
import ua.tihonchik.dmitriy.interfaces.NotificationConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class NotificationConverterImpl implements NotificationConverter {

    private String expression = "";
    private LocalDateTime eventDate = LocalDateTime.now();

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

        this.expression = expression.toLowerCase();
        this.eventDate = eventDate;

        String cronExpression = getCronExpression();
        CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        LocalDate startDayForGenerator = getStartDayForGenerator();
        Date firstNotificationDate = Date.from(startDayForGenerator.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nextRunDate = generator.next(firstNotificationDate);
        return nextRunDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    private LocalDate getStartDayForGenerator() {
        LocalDate notificationDate = getTheFirstNotificationDate();
        LocalDate notificationDateExcluded = notificationDate.minusDays(1);
        if (LocalDate.now().isAfter(notificationDateExcluded)) {
            return LocalDate.now();
        }
        return notificationDateExcluded;
    }

    private String getCronExpression() {

        String duration = getStringDuration();
        LocalDate firstNotificationDate = getTheFirstNotificationDate();
        String dayOfWeek = firstNotificationDate.getDayOfWeek().toString().substring(0, 3);
        String month = firstNotificationDate.getMonth().toString().substring(0, 3);
        int dayOfMonth = firstNotificationDate.getDayOfMonth();
        String regex = "";
        switch (duration) {
            case "day":
                regex = "0 0 0 * * ?";
                break;
            case "week":
                regex = "0 0 0 * * " + dayOfWeek;
                break;
            case "month":
                regex = "0 0 0 " + dayOfMonth + " * ?";
                break;
            case "year":
                regex = "0 0 0 " + dayOfMonth + " " + month + " ?";
        }

        return regex;
    }

    private LocalDate getTheFirstNotificationDate() {
        int countOfDays = getCountDayBeforeEvent();
        return eventDate.minusDays(countOfDays).toLocalDate();
    }

    private String getStringDuration() {
        return expression.replaceAll("(\\d|\\W)", "");
    }

    private int getCountDayBeforeEvent() {
        return Integer.parseInt(expression.replaceAll("(\\D|\\W)", ""));
    }

}
