package com.overlookManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overlookManagement.dto.BookingDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RoomDTO{

    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String role;
    private String roomDescription;
    private List<BookingDTO> bookings = new ArrayList<>();
}


