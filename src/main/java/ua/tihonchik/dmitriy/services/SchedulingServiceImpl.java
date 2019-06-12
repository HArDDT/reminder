package ua.tihonchik.dmitriy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.SimplifiedUser;
import ua.tihonchik.dmitriy.repositories.SchedulingRepository;

import java.util.List;
import java.util.Map;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    private SchedulingRepository repository;
    private Logger logger = LoggerFactory.getLogger(SchedulingServiceImpl.class);

    public SchedulingServiceImpl(SchedulingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<SimplifiedUser, List<Event>> getData() {
        Map<SimplifiedUser, List<Event>> data = repository.getData();
        return null;
    }

}
