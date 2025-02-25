package com.overlookManagement.dto;

import java.util.List;
import lombok.Data;
import com.overlookManagement.dto.*;


@Data
public class Response {
    private int statusCode;
    private String message;  // if is sessessful or not
    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;
    private BookingDTO booking;
    private List<UserDTO> userList;
    private List<RoomDTO> roomList;
    private List<BookingDTO> bookingList;

}
