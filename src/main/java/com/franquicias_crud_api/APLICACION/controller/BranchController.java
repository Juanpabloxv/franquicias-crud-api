package com.franquicias_crud_api.APLICACION.controller;

import com.franquicias_crud_api.APLICACION.dto.BranchDTO;
import com.franquicias_crud_api.APLICACION.dto.UpdateNameDTO;
import com.franquicias_crud_api.APLICACION.model.Branch;
import com.franquicias_crud_api.APLICACION.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/branches")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public List<BranchDTO> getAllBranches() {
        return branchService.getBranches();
    }

    @PostMapping
    public ResponseEntity<Object> createBranch(@RequestBody Branch branch) {
        return branchService.createBranch(branch);
    }

    @PutMapping
    public ResponseEntity<Object> updateBranch(@RequestBody Branch branch) {
        return branchService.updateBranch(branch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBranch(@PathVariable("id") Integer id) {
        return branchService.deleteBranch(id);
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    public ResponseEntity<Object> deleteProductFromBranch(
            @PathVariable Integer branchId,
            @PathVariable Integer productId) {
        return branchService.deleteProductFromBranch(branchId, productId);
    }

    @PatchMapping("/{branchId}/name")
    public ResponseEntity<Object> updateBranchName(
            @PathVariable Integer branchId,
            @RequestBody UpdateNameDTO nameDTO) {
        return branchService.updateBranchName(branchId, nameDTO.getName());
    }
}
