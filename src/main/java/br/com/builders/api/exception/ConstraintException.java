package br.com.builders.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ConstraintException.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintException extends RuntimeException {

  public static final long serialVersionUID = 1L;

  public ConstraintException(String message) {
    super(message);
  }


}
