package ua.tihiy.reminder.scheduling;

import org.springframework.stereotype.Component;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverter;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverterDay;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverterMonth;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverterWeek;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverterYear;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@Component
public class SchedulingConverterFactory {

    public Optional<SchedulingConverter> getNotificationConverter(String expression){
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

    private SchedulingConverter getConverter(@NotNull String expression){

        if (expression.contains("day")) {
            return new SchedulingConverterDay();
        }

        if (expression.contains("week")) {
            return new SchedulingConverterWeek();
        }

        if (expression.contains("month")) {
            return new SchedulingConverterMonth();
        }

        if (expression.contains("year")) {
            return new SchedulingConverterYear();
        }

        return null;

    }

}
