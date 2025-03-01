package com.overlookManagement.service.interfac;

import com.overlookManagement.dto.LoginRequest;
import com.overlookManagement.dto.Response;
import com.overlookManagement.entity.User;

public interface IUserService{
    Response register (User loginRequest);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}