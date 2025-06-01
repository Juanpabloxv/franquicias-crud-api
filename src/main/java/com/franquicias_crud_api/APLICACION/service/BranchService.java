package com.franquicias_crud_api.APLICACION.service;

import com.franquicias_crud_api.APLICACION.dto.BranchDTO;
import com.franquicias_crud_api.APLICACION.dto.FranchiseDTO;
import com.franquicias_crud_api.APLICACION.model.Branch;
import com.franquicias_crud_api.APLICACION.model.Franchise;
import com.franquicias_crud_api.APLICACION.model.Product;
import com.franquicias_crud_api.APLICACION.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<BranchDTO> getBranches() {
        return branchRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> createBranch(Branch branch) {
        Map<String, Object> msg = new HashMap<>();

        if (branch.getId() != null) {
            msg.put("error", true);
            msg.put("message", "La sucursal nueva no debe tener ID");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Branch> existing = branchRepository.findBranchByName(branch.getName());
        if (existing.isPresent()) {
            msg.put("error", true);
            msg.put("message", "Ya existe una sucursal con ese nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Branch saved = branchRepository.save(branch);
        msg.put("success", true);
        msg.put("message", "Sucursal creada exitosamente");
        msg.put("data", convertToDTO(saved));
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateBranch(Branch branch) {
        Map<String, Object> msg = new HashMap<>();

        if (branch.getId() == null) {
            msg.put("error", true);
            msg.put("message", "El ID es obligatorio para actualizar");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        Optional<Branch> existing = branchRepository.findById(branch.getId());
        if (existing.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró la sucursal");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Optional<Branch> withSameName = branchRepository.findBranchByName(branch.getName());
        if (withSameName.isPresent() && !Objects.equals(withSameName.get().getId(), branch.getId())) {
            msg.put("error", true);
            msg.put("message", "Ya existe otra sucursal con ese nombre");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        Branch updated = branchRepository.save(branch);
        msg.put("success", true);
        msg.put("message", "Sucursal actualizada exitosamente");
        msg.put("data", convertToDTO(updated));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteBranch(Integer id) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Branch> existing = branchRepository.findById(id);
        if (existing.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "No se encontró la sucursal a eliminar");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        branchRepository.deleteById(id);
        msg.put("success", true);
        msg.put("message", "Sucursal eliminada exitosamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteProductFromBranch(Integer branchId, Integer productId) {
        Map<String, Object> response = new HashMap<>();

        Optional<Branch> branchOpt = branchRepository.findById(branchId);
        if (branchOpt.isEmpty()) {
            response.put("error", true);
            response.put("message", "Sucursal no encontrada");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Branch branch = branchOpt.get();
        List<Product> products = branch.getProducts();

        boolean removed = products.removeIf(product -> product.getId().equals(productId));

        if (!removed) {
            response.put("error", true);
            response.put("message", "Producto no encontrado en la sucursal");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Guardamos el cambio en la sucursal
        branchRepository.save(branch);

        response.put("success", true);
        response.put("message", "Producto eliminado de la sucursal exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateBranchName(Integer branchId, String newName) {
        Map<String, Object> msg = new HashMap<>();

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if (optionalBranch.isEmpty()) {
            msg.put("error", true);
            msg.put("message", "Sucursal no encontrada");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        Branch branch = optionalBranch.get();
        branch.setName(newName);
        branchRepository.save(branch);

        msg.put("success", true);
        msg.put("message", "Nombre de la sucursal actualizado correctamente");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    public BranchDTO convertToDTO(Branch branch) {
        Franchise franchise = branch.getFranchise();
        FranchiseDTO franchiseDTO = new FranchiseDTO(franchise.getId(), franchise.getName());

        return new BranchDTO(branch.getId(), branch.getName(), franchiseDTO);
    }

}
