package ua.tihiy.reminder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventDeleteException extends IllegalArgumentException {
    public EventDeleteException(String message) {
        super(message);
    }

    public EventDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}