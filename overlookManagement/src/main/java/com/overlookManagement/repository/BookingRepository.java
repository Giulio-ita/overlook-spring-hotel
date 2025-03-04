package com.overlookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.overlookManagement.entity.Booking;
import java.util.List;
import java.util.Optional;

// interface, not classs
public interface BookingRepository extends JpaRepository <Booking, Long> {
    //List<Booking> findByRoomId(Long roomId);

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    //List<Booking> findByUserId(Long userId);

}
