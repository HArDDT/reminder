package ua.tihonchik.dmitriy.entities;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//3week - 3 days before the date of the event, every week repeat
//12year - 12 days before the date of the event, every year repeat
//2day - 2 days before the date of the event, every day repeat

public class NotificationConverterImpl {

    private String expression;
    private LocalDateTime eventDate;

    public NotificationConverterImpl(String expression, LocalDateTime eventDate) {
        this.expression = expression.toLowerCase();
        this.eventDate = eventDate;
    }

    public LocalDate getNextValidDate() {

        String cronExpression = getCronExpression();
        CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);
        Date nextRunDate = generator.next(new Date());
        return nextRunDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    private String getCronExpression() {

        String duration = expression.replaceAll("(\\d|\\W)","");
        String regex = "";
        switch (duration) {
            case "week":
                regex = "Color is Red";
                break;
            case "year":
                regex = "Color is Green";
                break;
            case "day":
                regex = "Color is Green";
        }

        return regex;
    }

    private LocalDate getTheFirstNotificationDate(){
        Integer countOfDays = Integer.getInteger(expression.replaceAll("(\\D|\\W)",""));
        return eventDate.minusDays(countOfDays).toLocalDate();
    }

}
