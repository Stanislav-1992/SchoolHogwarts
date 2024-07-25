package ru.hogwarts.schoolspring.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class AvatarProcessingException extends RuntimeException{
    public AvatarProcessingException() {
        super();
    }
}
