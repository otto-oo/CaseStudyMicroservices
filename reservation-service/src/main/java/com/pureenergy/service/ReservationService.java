package com.pureenergy.service;

import com.pureenergy.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> getReservationsByMovieId(Long movieId);

    ReservationDTO createReservation(ReservationDTO reservationDTO);

    ReservationDTO deleteReservation(Long id);
}

