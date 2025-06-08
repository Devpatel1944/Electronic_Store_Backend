package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Product")
public class ProductController {

   @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);


    //Create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.createProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/update/{productId}")
      public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId ,@RequestBody ProductDto productDto){
        ProductDto productDto1 =productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
      }


    //Delete
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Product Deleted Sucesfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);

    }

    //GetSingle
    @GetMapping("/getSingle/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
               ProductDto productDto =productService.getSingleProduct(productId);
               return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //GetAll
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<ProductDto>> GetAllProduct(
            @RequestParam(name = "pageNumber" ,defaultValue ="0",required = false) int pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(name ="sortDir",defaultValue = "asc",required = false) String sortDir){
          PageableResponse<ProductDto> pageableResponse =productService.getAllProduct(pageNumber,pageSize,sortBy,sortDir);
          return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }


    //Get Is Live
    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>> GetAllLiveProduct(
            @RequestParam(name = "pageNumber" ,defaultValue ="0",required = false) int pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(name ="sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse =productService.getIsAlive(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // Search By Title
    @GetMapping("/getAll/{subTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> GetProductSearch(
            @PathVariable String subTitle,
            @RequestParam(name = "pageNumber" ,defaultValue ="0",required = false) int pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(name ="sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse =productService.searchByTitle(subTitle,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }


}

