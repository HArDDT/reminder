package ua.tihiy.reminder.scheduling;

import org.springframework.stereotype.Component;
import ua.tihiy.reminder.scheduling.converters.NotificationConverter;
import ua.tihiy.reminder.scheduling.converters.NotificationConverterDay;
import ua.tihiy.reminder.scheduling.converters.NotificationConverterMonth;
import ua.tihiy.reminder.scheduling.converters.NotificationConverterWeek;
import ua.tihiy.reminder.scheduling.converters.NotificationConverterYear;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@Component
public class NotificationConverterFactory {

    public Optional<NotificationConverter> getNotificationConverter(String expression){
        if (isValidExpression(expression)){
            return Optional.ofNullable(getConverter(expression));
        }
        else {
            return Optional.empty();
        }
    }

    private boolean isValidExpression(String expression) {
        return Objects.nonNull(expression) && !expression.isBlank() && expression.matches("^\\d{1,3}\\s?([,/.])?\\s?(day|week|month|year)$");
    }

    private NotificationConverter getConverter(@NotNull String expression){

        if (expression.contains("day")) {
            return new NotificationConverterDay();
        }

        if (expression.contains("week")) {
            return new NotificationConverterWeek();
        }

        if (expression.contains("month")) {
            return new NotificationConverterMonth();
        }

        if (expression.contains("year")) {
            return new NotificationConverterYear();
        }

        return null;

    }

}
