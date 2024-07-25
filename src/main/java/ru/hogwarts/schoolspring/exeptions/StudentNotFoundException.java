package ru.hogwarts.schoolspring.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException  extends RuntimeException{
    public StudentNotFoundException() {
        super("Студент не найден");
    }
}
