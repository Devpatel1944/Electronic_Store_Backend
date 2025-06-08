package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product =mapper.map(productDto, Product.class);
        //Random ProductId
        product.setProductId(UUID.randomUUID().toString());
        //Generate Date
        product.setAddedDate(new Date());
        Product savedProduct =productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
       Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product Id is Invalid"));
       product.setTitle(productDto.getTitle());
       product.setDescription(productDto.getDescription());
       product.setPrice(productDto.getPrice());
       product.setDiscountedPrice(productDto.getDiscountedPrice());
       product.setQuantity(productDto.getQuantity());
       product.setLive(productDto.isLive());
       product.setStock(productDto.isStock());

       Product product1 = productRepository.save(product);

        return mapper.map(product1,ProductDto.class);

    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product Id is Invalid"));
        productRepository.delete(product);
        logger.info("Product Deleted Sucessfully");
    }



    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product Id is Invalid"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber , int pageSize ,String sortBy ,String sortDir) {

        Sort sort = (sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pagable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subtitle,int pageNumber , int pageSize ,String sortBy ,String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subtitle,pagable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getIsAlive(int pageNumber , int pageSize ,String sortBy ,String sortDir) {

        Sort sort = (sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pagable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("INVALID CATEGORY ID"));
        Product product =mapper.map(productDto, Product.class);
        //Random ProductId
        product.setProductId(UUID.randomUUID().toString());
        //Generate Date
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct =productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product =productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product Id INVALID!!"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("category Id INVALID!!"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getProductforSpecificCategory(String categoryId,int pageNumber , int pageSize ,String sortBy ,String sortDir) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("category Id INVALID!!"));
        Sort sort = (sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable =PageRequest.of(pageNumber,pageSize,sort);
      Page<Product> page=  productRepository.findByCategory(category ,pagable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }


}
