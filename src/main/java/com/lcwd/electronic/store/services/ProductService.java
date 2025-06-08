package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

public interface ProductService {
    //Create
    ProductDto createProduct(ProductDto productDto);

    //Update
    ProductDto updateProduct(ProductDto productDto ,String productId);

    //Delete
    void deleteProduct(String productId);

    //GetAll
    PageableResponse<ProductDto> getAllProduct(int pageNumber , int pageSize ,String sortBy ,String sortDir);

    //GetSingle
    ProductDto getSingleProduct(String productId);

    //SearchByTitle
    PageableResponse<ProductDto> searchByTitle(String subtitle,int pageNumber , int pageSize ,String sortBy ,String sortDir);

    //Get Live Product
    PageableResponse<ProductDto> getIsAlive(int pageNumber , int pageSize ,String sortBy ,String sortDir);

    //create product with category
    ProductDto createProductWithCategory(ProductDto productDto ,String categoryId);

    //Update Category
    ProductDto updateCategory(String productId,String categoryId);

    //get All Product for Specific Category
    PageableResponse<ProductDto> getProductforSpecificCategory(String categoryId,int pageNumber , int pageSize ,String sortBy ,String sortDir);

}
