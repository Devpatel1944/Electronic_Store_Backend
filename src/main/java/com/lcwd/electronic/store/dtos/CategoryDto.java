package com.lcwd.electronic.store.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "It's Required")
    @Size(min = 3 ,max = 100)
    private String title;
     @NotBlank(message= "It's Required")
    private String description;

}
