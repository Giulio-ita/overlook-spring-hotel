package com.overlookManagement.service.interfac;

import com.overlookManagement.dto.Response;
import com.overlookManagement.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode); 
    
    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}
