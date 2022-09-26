package com.pureenergy.dto;

import com.pureenergy.enums.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class LogDTO {

    private LocalDate logDate;
    private Operation operation;
    private String message;

    public LogDTO(LocalDate logDate, Operation operation, String message) {
        this.logDate = logDate;
        this.operation = operation;
        this.message = message;
    }

}
