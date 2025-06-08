package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto ceateCategory(CategoryDto categoryDto) {
        //create Random categoryId
        String categoryId  = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto,Category.class);
       Category savedCategory= categoryRepository.save(category);
       CategoryDto categoryDto1 =mapper.map(savedCategory,CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Invalid CategoryId"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        CategoryDto categoryDto1 = mapper.map(category,CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Invalid CategoryId"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize ,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pagable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page,CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Invalid CategoryId"));
        CategoryDto categoryDto1 = mapper.map(category,CategoryDto.class);

        return categoryDto1;
    }
}
