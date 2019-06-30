package ua.tihiy.reminder.events.repeatable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class EventController {

    private RepeatableEventService repeatableEventService;

    public EventController(RepeatableEventService repeatableEventService) {
        this.repeatableEventService = repeatableEventService;
    }

    @GetMapping(value = "/protected/events/{userId}")
    public Collection<RepeatableEvent> getEvents(@PathVariable int userId) {
        return repeatableEventService.getEvents(userId);
    }

    @GetMapping(value = "/protected/event/{eventId}")
    public RepeatableEvent getEvent(@PathVariable int eventId) {
        return repeatableEventService.getEvent(eventId);
    }

    @PostMapping(value = "/protected/event")
    public Object createEvent(@RequestBody RepeatableEvent repeatableEvent) {
        return repeatableEventService.createEvent(repeatableEvent);
    }

    @PutMapping(value = "/protected/event")
    public void updateEvent(@RequestBody RepeatableEvent repeatableEvent) {
        repeatableEventService.updateEvent(repeatableEvent);
    }

    @DeleteMapping(value = "/protected/event/{eventId}")
    public void deleteEvent(@PathVariable int eventId) {
        repeatableEventService.deleteEvent(eventId);
    }

}
