package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //Create User
    UserDto createUser(UserDto userDto);

    //Update User
    UserDto updateUser(UserDto userDto,String userId);

    //Delete User
    void deleteUser(String userId);

    //Get All Users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get Single User By Id
    UserDto getUserById(String userId);

    //Get Single User By Email
    UserDto getUserByEmail(String email);

    //Search User
    List<UserDto> searchUser(String keyWord);


}
