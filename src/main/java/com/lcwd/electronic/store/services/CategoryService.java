package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //Create
    CategoryDto ceateCategory(CategoryDto categoryDto);

    //Update
    CategoryDto updateCategory(CategoryDto categoryDto ,String categoryId);

    // Delete
    void  deleteCategory(String categoryId);

    //Get All
    PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize ,String sortBy,String sortDir);

    //Get Single Category
    CategoryDto getCategory(String categoryId);
}



