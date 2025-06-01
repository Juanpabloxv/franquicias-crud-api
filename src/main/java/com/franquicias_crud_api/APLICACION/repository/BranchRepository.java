package com.franquicias_crud_api.APLICACION.repository;

import com.franquicias_crud_api.APLICACION.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findBranchByName(String name);
}