package bobr.navigatorMicroserviceWeb.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class NoSuchElementExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleExceptions(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

}
