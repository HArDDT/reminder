package ua.tihonchik.dmitriy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.tihonchik.dmitriy.additional.NotificationConverter;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;
import ua.tihonchik.dmitriy.repositories.SchedulingRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    private SchedulingRepository repository;
    private Logger logger = LoggerFactory.getLogger(SchedulingServiceImpl.class);
    private NotificationConverter converter;
    private EmailService emailService;

    public SchedulingServiceImpl(SchedulingRepository repository, NotificationConverter converter, EmailService emailService) {
        this.repository = repository;
        this.converter = converter;
        this.emailService = emailService;
    }

    @Override
    public Map<SimplifiedUser, List<Event>> getData() {
        return repository.getData();
    }

    @Override
    public Map<String, String> emailsWithMessage(){

        Map<SimplifiedUser, List<Event>> data = getData();
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
        return ((List<Event>) mapEntry.getValue()).stream()
                .filter(event -> converter.getNextValidDate(event.getReminderExpression(), event.getEventDate())
                        .isEqual(LocalDate.now()))
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
