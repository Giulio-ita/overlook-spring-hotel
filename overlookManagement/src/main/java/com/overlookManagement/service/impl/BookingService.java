package com.overlookManagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.overlookManagement.dto.BookingDTO;
import com.overlookManagement.dto.Response;
import com.overlookManagement.entity.Booking;
import com.overlookManagement.entity.Room;
import com.overlookManagement.entity.User;
import com.overlookManagement.exception.OurException;
import com.overlookManagement.repository.BookingRepository;
import com.overlookManagement.repository.RoomRepository;
import com.overlookManagement.repository.UserRepository;
import com.overlookManagement.service.interfac.IBookingService;
import com.overlookManagement.service.interfac.IRoomService;
import com.overlookManagement.utils.Utils;

public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    // userId is the User thant is making a booking and the Room that want to book
    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();
        try{
            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate()))
                throw new IllegalArgumentException("Checkin date must come after checkOut date");
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            // check if the user, who wants to book the roomId, exists
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User not found"));
            List<Booking> existingBookings = room.getBookings();
            if(!roomIsAvailable(bookingRequest, existingBookings))
                throw new OurException("Room non available for selected date range");

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCOde = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCOde);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("succesfull");
            response.setBookingConfirmationCode(bookingConfirmationCOde);                    
        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a booking" + e.getMessage());
        }
        return response;    
    }
    
    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try{
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);
            response.setStatusCode(200);
            response.setMessage("succesful");
            response.setBooking(bookingDTO);

        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a booking" + e.getMessage());
        }
        return response;        
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        try{
            Booking bookings = bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking does not exists"));
            // only if the exception is not captured
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("succesful");

        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error cancelling a booking" + e.getMessage());
        }
        return response; 
    }

    public boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
        .noneMatch(existingBooking ->
                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
        );
    }
    
}
