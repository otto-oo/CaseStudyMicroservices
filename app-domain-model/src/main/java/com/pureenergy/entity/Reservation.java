package com.pureenergy.entity;

import com.pureenergy.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="reservations")
public class Reservation extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String reservationName;
    private LocalDateTime reservationDateTime;

    @Enumerated(EnumType.STRING)
    private Status reservationStatus;

}
