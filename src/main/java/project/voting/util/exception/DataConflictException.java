package project.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "DataBase conflict")
public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}