package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    //Create
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.ceateCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Delete Category Sucessfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    //GetAll
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //Get Single
    @GetMapping("/getSingleCategory/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(
            @PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    //Create Product With Category
    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId, @RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //Update Category of Product
    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId, @PathVariable String productId) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //get Product of Specific Category
    //Update Category of Product
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductOfCategory(@PathVariable String categoryId,
                                                                             @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                             @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir                                                                ) {
       PageableResponse<ProductDto> response = productService.getProductforSpecificCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}