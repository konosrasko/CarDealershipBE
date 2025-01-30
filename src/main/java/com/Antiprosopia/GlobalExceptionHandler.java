package com.Antiprosopia;

import com.Antiprosopia.car.CarException;
import com.Antiprosopia.reservation.ReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<Map<String, String>> handleReservationException(ReservationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarException.class)
    public ResponseEntity<ResponseDTO> handleCarException(CarException ex) {
        return new ResponseEntity<>(new ResponseDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
