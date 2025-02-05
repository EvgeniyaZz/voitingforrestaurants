package project.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Resource not found")  // 422
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}