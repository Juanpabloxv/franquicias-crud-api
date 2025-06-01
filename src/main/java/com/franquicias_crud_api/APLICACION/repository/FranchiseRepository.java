package com.franquicias_crud_api.APLICACION.repository;

import com.franquicias_crud_api.APLICACION.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {
    Optional<Franchise> findByName(String name);
}
