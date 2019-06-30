package ua.tihiy.reminder.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.tihiy.reminder.mail.EmailService;
import ua.tihiy.reminder.events.repeatable.RepeatableEvent;
import ua.tihiy.reminder.scheduling.converters.SchedulingConverter;
import ua.tihiy.reminder.users.SimplifiedUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    private SchedulingRepository repository;
    private Logger logger = LoggerFactory.getLogger(SchedulingServiceImpl.class);
    private EmailService emailService;
    private SchedulingConverterFactory factory;

    public SchedulingServiceImpl(SchedulingRepository repository,
                                 EmailService emailService,
                                 SchedulingConverterFactory factory) {
        this.repository = repository;
        this.emailService = emailService;
        this.factory = factory;
    }

    /**
     * SchedulingServiceImpl - return the next valid date of notification
     *
     * @param expression - the template should have the number of days before the event date and the frequency of recurrence (day, week, month, year).
     *                   Like this: "3week", "0, year", "2 day", "1 month".
     * @param eventDate  - real date of event
     *                   {@link SchedulingServiceImpl#getNextValidDate(String, LocalDateTime)}
     */

    @Override
    public LocalDate getNextValidDate(String expression, LocalDateTime eventDate){
        SchedulingConverter converter = factory.getNotificationConverter(expression).
                orElseThrow(() -> new IllegalArgumentException("Incorrect expression: " + expression + "\n" +
                        "The template should have the number of days before the event date and the frequency of recurrence (day, week, month, year).\n" +
                        "Like this: \"3week\", \"0, year\", \"2 day\", \"1 month\""));
        return converter.getNextValidDate(expression, eventDate);
    }

    @Override
    public Map<SimplifiedUser, List<RepeatableEvent>> getData() {
        return repository.getData();
    }

    @Override
    public Map<String, String> emailsWithMessage(){

        Map<SimplifiedUser, List<RepeatableEvent>> data = getData();
        Map<String, String> emailsWithMessage = new HashMap<>();
        for (Map.Entry mapEntry : data.entrySet()) {
            String events = getEvents(mapEntry);
            if (events.isBlank()){
                continue;
            }

            emailsWithMessage.put(
                    ((SimplifiedUser) mapEntry.getKey()).getEmail(),
                    events);
        }
        return emailsWithMessage;

    }

    private String getEvents(Map.Entry mapEntry) {
        return ((List<RepeatableEvent>) mapEntry.getValue()).stream()
                .filter(event -> getNextValidDate(
                        event.getReminderExpression(),
                        event.getEventDate()).isEqual(LocalDate.now()))
                .map(event -> event.getEventDate() + " : " + event.getDescription())
                .collect(Collectors.joining("\n"));
    }

    @Scheduled(cron = "1 0 0 * * ?")
    @Override
    public void sendEmails(){
        Map<String, String> recipientsWithMessages = emailsWithMessage();
        recipientsWithMessages.forEach((recipient, message) -> emailService.sendMessage(recipient,"notification" , message));
    }

}
