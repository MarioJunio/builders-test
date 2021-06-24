package br.com.builders.api.exception;

import br.com.builders.api.dto.ErrorView;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    final CacheControl cacheControl = CacheControl.noCache();

    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<ErrorView> handleConstraintExceptionException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .cacheControl(cacheControl).body(ErrorView.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorView> handleResourceNotFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .cacheControl(cacheControl).body(ErrorView.builder()
                        .message(ex.getMessage())
                        .build());
    }

}
