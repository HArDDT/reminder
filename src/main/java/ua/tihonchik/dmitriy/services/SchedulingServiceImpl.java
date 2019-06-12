package ua.tihonchik.dmitriy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public SchedulingServiceImpl(SchedulingRepository repository, NotificationConverter converter) {
        this.repository = repository;
        this.converter = converter;
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

}
