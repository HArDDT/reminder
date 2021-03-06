package ua.tihiy.reminder.scheduling.converters;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class SchedulingConverterDay implements SchedulingConverter {

    @Override
    public LocalDate getNextValidDate(String expression, LocalDateTime eventDate) {

        String lowerCaseExpression = expression.toLowerCase();
        String cronExpression = getCronExpression(eventDate, lowerCaseExpression);
        CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        LocalDate startDayForGenerator = getStartDayForGenerator(eventDate, lowerCaseExpression);
        Date firstNotificationDate = Date.from(startDayForGenerator.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nextRunDate = generator.next(firstNotificationDate);
        return nextRunDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

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
        return "59 59 12 * * ?";
    }

    private LocalDate getTheFirstNotificationDate(LocalDateTime eventDate, String expression) {
        int countOfDays = getCountDayBeforeEvent(expression);
        return eventDate.minusDays(countOfDays).toLocalDate();
    }

    private int getCountDayBeforeEvent(String expression) {
        return Integer.parseInt(expression.replaceAll("(\\D|\\W)", ""));
    }

}