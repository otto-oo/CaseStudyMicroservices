package com.pureenergy.entity;

import com.pureenergy.enums.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="logs")
public class Log extends BaseEntity{

    private LocalDate logDate;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    private String message;

    public Log(Operation operation, String message) {
        this.logDate = LocalDate.now();
        this.operation = operation;
        this.message = message;
    }
}
