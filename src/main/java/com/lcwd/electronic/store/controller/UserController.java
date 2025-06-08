package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
     private UserService userService;


    //Create User
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser( @Valid  @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @Valid @RequestBody UserDto userDto) {
        UserDto updateDto = userService.updateUser(userDto, userId);
         return new ResponseEntity<>(updateDto,HttpStatus.OK);
    }

    //Delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deletUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        ApiResponseMessage msg = ApiResponseMessage.builder()
                .message("User Is Complete Deleted")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    //GetAll Users
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam( value = "pageNumber" ,defaultValue ="0" ,required = false) int pageNumber,
            @RequestParam(value = "pageSize" ,defaultValue ="10",required = false) int pageSize,
            @RequestParam(value = "sortBy" ,defaultValue ="name",required = false) String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue ="asc",required = false) String sortDir
    ){
        PageableResponse<UserDto> userDto = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //Get Single User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //Get User By Email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getByemail(@PathVariable("email") String email){
        UserDto userDto = userService.getUserByEmail(email);
         return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //Search User
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> SearchUser(@PathVariable("keyword") String keyword){
        List<UserDto> userDto = userService.searchUser(keyword);
         return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
