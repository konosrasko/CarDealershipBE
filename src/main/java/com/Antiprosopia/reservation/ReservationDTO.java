package com.Antiprosopia.reservation;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationDTO {
    private Integer reservationId;
    private Integer citizenId;
    private Integer carId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;

}
