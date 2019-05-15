package ua.tihonchik.dmitriy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tihonchik.dmitriy.entities.Event;
import ua.tihonchik.dmitriy.entities.EventImpl;
import ua.tihonchik.dmitriy.services.EventService;

import java.util.List;

@RestController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

//    @GetMapping(value = "/protected/getevents/{userid}")
//    public ResponseEntity<List<Event>> getEvent(@PathVariable int userId){
//        return new ResponseEntity(List.of("2","1"), HttpStatus.OK);
//    }

    @PostMapping(value = "/protected/create_event")
    public ResponseEntity<Integer> createEvent(@RequestBody EventImpl event){
        int idEvent = eventService.createEvent(event);
        return new ResponseEntity<>(idEvent, HttpStatus.OK);
    }

}
