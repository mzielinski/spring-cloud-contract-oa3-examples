package com.mzielinski.sccoa3.examples.error;

import com.mzielinski.sccoa3.examples.api.model.ErrorMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class.getName());

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleCustomException(MethodArgumentTypeMismatchException exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorMessageDto().message("Invalid Request");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto handleCustomException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorMessageDto().message("Unexpected Error");
    }
}
