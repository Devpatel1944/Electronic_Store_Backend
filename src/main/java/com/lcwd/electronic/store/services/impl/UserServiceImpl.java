package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        //Random USerId Generate
       String userId= UUID.randomUUID().toString();
          userDto.setUserId(userId);

        //DtoToUser
        User user = dtoToEntity(userDto);
        User savedUser =userRepository.save(user);
        //UserTODto
        UserDto userDto1 = entityTODto(savedUser);
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id Is Invalid"));
         user.setName(userDto.getName());
         user.setPassword(userDto.getPassword());
         user.setGender(userDto.getGender());
         user.setAbout(userDto.getAbout());
         user.setImageName(userDto.getImageName());
         UserDto newDto = entityTODto(user);
         return newDto;

    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id Is Invalid"));
        userRepository.delete(user);
    }


    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        //Sorting Order and By
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //Arrange PageSize and PageNumber
        Pageable pagable= PageRequest.of(pageNumber,pageSize,sort);

        Page<User> page =userRepository.findAll(pagable);
       PageableResponse<UserDto> response=  Helper.getPageableResponse(page,UserDto.class);

      return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id Is Invalid"));
        UserDto userDto = entityTODto(user);
        return userDto;

    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Email Is Invalid"));
        UserDto newDto =  entityTODto(user);
        return newDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
      List<UserDto> newDto=  users.stream().map(user ->entityTODto(user)).collect(Collectors.toList());
      return newDto;
    }

    private User dtoToEntity(UserDto userDto){
//      User user=  User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .gender(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .build();
        return mapper.map(userDto,User.class);
    }

    private UserDto entityTODto(User user){
//       UserDto userDto= UserDto.builder()
//                .userId(user.getUserId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .about(user.getAbout())
//                .gender(user.getGender())
//                .imageName(user.getImageName())
//                .build();
        return mapper.map(user,UserDto.class);
    }
}
