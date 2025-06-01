package com.franquicias_crud_api.APLICACION.service;

import com.franquicias_crud_api.APLICACION.dto.BranchDTO;
import com.franquicias_crud_api.APLICACION.dto.FranchiseDTO;
import com.franquicias_crud_api.APLICACION.dto.ProductDTO;
import com.franquicias_crud_api.APLICACION.model.Branch;
import com.franquicias_crud_api.APLICACION.model.Franchise;
import com.franquicias_crud_api.APLICACION.model.Product;
import com.franquicias_crud_api.APLICACION.repository.FranchiseRepository;
import com.franquicias_crud_api.APLICACION.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, FranchiseRepository franchiseRepository){
        this.productRepository = productRepository;
        this.franchiseRepository = franchiseRepository;
    }

    public List<ProductDTO> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getFranchiseById(Integer id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ResponseEntity<Object> createProduct(Product product) {
        HashMap<String, Object> msg = new HashMap<>();

        if (product.getId() != null) {
            msg.put("error", true);
            msg.put("message", "El producto nuevo no debe tener ID");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Product> existingProduct = productRepository.findByNameAndBranchId(
                product.getName(), product.getBranch().getId()
        );
        if (existingProduct.isPresent()) {
            msg.put("error", true);
            msg.put("message", "Ya existe un producto con este nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Product savedProduct = productRepository.save(product);
        msg.put("data", convertToDTO(savedProduct));
        msg.put("success", true);
        msg.put("message", "Producto creado exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateProduct(Product product) {
        HashMap<String, Object> msg = new HashMap<>();

        if (product.getId() == null) {
            msg.put("error", true);
            msg.put("message", "El ID del producto es obligatorio para actualizar");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró el producto a actualizar");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Optional<Product> productWithSameName = productRepository.findByNameAndBranchId(
                product.getName(), product.getBranch().getId()
        );
        if (productWithSameName.isPresent() && !productWithSameName.get().getId().equals(product.getId())) {
            msg.put("error", true);
            msg.put("message", "Ya existe otro producto con este nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Product updatedProduct = productRepository.save(product);
        msg.put("data", convertToDTO(updatedProduct));
        msg.put("success", true);
        msg.put("message", "Producto actualizado exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteProduct(Integer id) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Product> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró el Producto a eliminar");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(id);
        msg.put("success", true);
        msg.put("message", "Producto eliminado exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateProductStock(Integer productId, Integer newStock) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "Producto no encontrado");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();
        product.setStock(newStock);
        productRepository.save(product);

        msg.put("success", true);
        msg.put("message", "Stock actualizado correctamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTopStockProductsByFranchise(Integer franchiseId) {
        Map<String, Object> msg = new HashMap<>();

        if (!franchiseRepository.existsById(franchiseId)) {
            msg.put("error", true);
            msg.put("message", "No se encontró la franquicia con ID: " + franchiseId);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        List<Product> topProducts = productRepository.findTopStockProductsByFranchise(franchiseId);
        List<ProductDTO> result = topProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateProductName(Integer productId, String newName) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "Producto no encontrado");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();
        product.setName(newName);
        productRepository.save(product);

        msg.put("success", true);
        msg.put("message", "Nombre del producto actualizado correctamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ProductDTO convertToDTO(Product product) {
        Franchise franchise = product.getBranch().getFranchise();
        FranchiseDTO franchiseDTO = new FranchiseDTO(franchise.getId(), franchise.getName());

        Branch branch = product.getBranch();
        BranchDTO branchDTO = new BranchDTO(branch.getId(), branch.getName(), franchiseDTO);

        return new ProductDTO(product.getId(), product.getName(), product.getStock(), branchDTO);
    }
}