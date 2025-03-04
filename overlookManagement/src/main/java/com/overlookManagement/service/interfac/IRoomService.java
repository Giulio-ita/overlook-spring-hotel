package com.overlookManagement.service.interfac;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.overlookManagement.dto.Response;

public interface IRoomService {
    
    Response addNewRoom(MultipartFile photo, String toomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();
    
    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Response getRoomById(Long roomId);

    Response getAllAvailableRooms();

    Response getAvailableRoomsByDateAndType(LocalDate checkinDate, LocalDate checkoutDate, String roomType);

    Response getAllRooms();


}
