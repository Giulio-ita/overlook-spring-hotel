package com.overlookManagement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.overlookManagement.entity.Booking;
import com.overlookManagement.entity.Room;
import com.overlookManagement.entity.User;

public class BookingDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numOfAdults;;
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private User user;
    private Room rom;
    private List<Booking> bookings = new ArrayList<>();
    
}
