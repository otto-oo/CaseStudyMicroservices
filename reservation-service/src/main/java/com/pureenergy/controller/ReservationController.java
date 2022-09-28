package com.pureenergy.controller;

import com.pureenergy.dto.ReservationDTO;
import com.pureenergy.entity.ResponseWrapper;
import com.pureenergy.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ResponseWrapper> getReservationsByMovieId(@PathVariable("movieId") Long movieId){
        return ResponseEntity
                .ok(new ResponseWrapper("Reservations that depends on movie id " + movieId + " are retrieved.",reservationService.getReservationsByMovieId(movieId)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createReservation(@RequestBody ReservationDTO reservationDTO){
        return ResponseEntity
                .ok(new ResponseWrapper("Reservation is created.", reservationService.createReservation(reservationDTO)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper> deleteReservationsById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(new ResponseWrapper("Reservation is canceled depends on reservation id " + id + ".",reservationService.deleteReservation(id)));
    }
}