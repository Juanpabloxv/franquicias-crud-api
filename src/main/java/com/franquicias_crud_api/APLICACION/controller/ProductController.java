package com.franquicias_crud_api.APLICACION.controller;

import com.franquicias_crud_api.APLICACION.dto.ProductDTO;
import com.franquicias_crud_api.APLICACION.dto.UpdateNameDTO;
import com.franquicias_crud_api.APLICACION.dto.UpdateStockDTO;
import com.franquicias_crud_api.APLICACION.model.Product;
import com.franquicias_crud_api.APLICACION.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getFranchiseById(@PathVariable Integer id) {
        return productService.getFranchiseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object>  insertProducts(@RequestBody Product product){
        return this.productService.createProduct(product);
    }

    @PutMapping
    public ResponseEntity<Object> updateProducts(@RequestBody Product product){
        return this.productService.updateProduct(product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Object> updateProductStock(
            @PathVariable Integer productId,
            @RequestBody UpdateStockDTO stockDTO) {
        return productService.updateProductStock(productId, stockDTO.getStock());
    }

    @GetMapping("/franchises/{franchiseId}/top-stock")
    public ResponseEntity<Object> getTopStockProductsByFranchise(
            @PathVariable Integer franchiseId) {
        return productService.getTopStockProductsByFranchise(franchiseId);
    }

    @PatchMapping("/{productId}/name")
    public ResponseEntity<Object> updateProductName(
            @PathVariable Integer productId,
            @RequestBody UpdateNameDTO nameDTO) {
        return productService.updateProductName(productId, nameDTO.getName());
    }
}
