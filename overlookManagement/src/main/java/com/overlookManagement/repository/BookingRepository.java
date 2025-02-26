package com.overlookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.overlookManagement.entity.Booking;
import java.util.List;

// interface, not classs
public interface BookingRepository extends JpaRepository <Booking, Long> {
    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByBookingConfirmationCode(String confirmationCOde);

    List<Booking> findByUserId(Long userId);

}
