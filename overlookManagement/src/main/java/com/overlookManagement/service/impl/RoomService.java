package com.overlookManagement.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.overlookManagement.dto.Response;
import com.overlookManagement.dto.RoomDTO;
import com.overlookManagement.entity.Room;
import com.overlookManagement.exception.OurException;
import com.overlookManagement.repository.BookingRepository;
import com.overlookManagement.repository.RoomRepository;
import com.overlookManagement.service.interfac.IRoomService;
import java.time.LocalDate;
import com.overlookManagement.utils.Utils;


@Service
public class RoomService implements IRoomService {
    // pass a image when new Room is created
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    

    @Override
    public Response addNewRoom(MultipartFile photo, String toomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        try{
            //String imageUrl = aws3service.saveimagestoS3(photo);
            Room room = new Room();
            //room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(toomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom); 
            response. setStatusCode(200);
            response.setMessage("succesful");
            response.setRoom(roomDTO);            
        }catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }     
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response. setStatusCode(200);
            response.setMessage("succesful");           
        }catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }     
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkinDate, LocalDate checkoutDate, String roomType) {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response. setStatusCode(200);
            response.setMessage("succesful");
            response.setRoomList(roomDTOList);           
        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }   
        return response;
      }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try{
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response. setStatusCode(200);
            response.setMessage("succesful");
            response.setRoom(roomDTO);           
        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }   
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, org.springframework.web.multipart.MultipartFile photo) {
        Response response = new Response();
        try{
            String imageUrl = null;
            //if(photo != null && !photo.isEmpty()){
            //    imageUrl = aws3service.saveImage(photo);
            
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if(roomType != null) room.setRoomType(roomType);
            if(roomPrice != null) room.setRoomPrice(roomPrice);
            if(description != null) room.setRoomDescription(description);
            if(imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updateRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updateRoom);
 
            response. setStatusCode(200);
            response.setMessage("succesful");           
        }catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }      
        return response;
    }
    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try{
            roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
            roomRepository.deleteById(roomId);
            response. setStatusCode(200);
            response.setMessage("succesful");           
        }catch(OurException e){
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }     
        return response;
    }
    
}
