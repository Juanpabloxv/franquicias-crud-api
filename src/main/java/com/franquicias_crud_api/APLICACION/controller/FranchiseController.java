package com.franquicias_crud_api.APLICACION.controller;

import com.franquicias_crud_api.APLICACION.dto.FranchiseDTO;
import com.franquicias_crud_api.APLICACION.dto.UpdateNameDTO;
import com.franquicias_crud_api.APLICACION.model.Franchise;
import com.franquicias_crud_api.APLICACION.service.FranchiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping
    public List<FranchiseDTO> getAllFranchises() {
        return franchiseService.getFranchises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FranchiseDTO> getFranchiseById(@PathVariable Integer id) {
        return franchiseService.getFranchiseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createFranchise(@RequestBody Franchise franchise) {
        return franchiseService.createFranchise(franchise);
    }

    @PutMapping
    public ResponseEntity<Object> updateFranchise(@RequestBody Franchise franchise) {
        return franchiseService.updateFranchise(franchise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFranchise(@PathVariable Integer id) {
        return franchiseService.deleteFranchise(id);
    }

    @PatchMapping("/{franchiseId}/name")
    public ResponseEntity<Object> updateFranchiseName(
            @PathVariable Integer franchiseId,
            @RequestBody UpdateNameDTO nameDTO) {
        return franchiseService.updateFranchiseName(franchiseId, nameDTO.getName());
    }

}
