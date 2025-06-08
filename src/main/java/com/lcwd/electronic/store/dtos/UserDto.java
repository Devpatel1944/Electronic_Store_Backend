package com.lcwd.electronic.store.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 4 ,max = 15 ,message = "Invalid Name!!")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n" , message = "Invalid User email")
    @NotBlank(message ="Invalid User email")
    private String email;

    @NotBlank(message = "Password is Required!!")
    private String password;

    @Size(min = 4,max = 6,message = "Invaild Gender!!")
    private String gender;

    @NotBlank(message = "Write something about yourself")
    private String about;

    private String imageName;

}
