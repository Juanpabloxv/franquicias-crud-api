package com.franquicias_crud_api.APLICACION.repository;

import com.franquicias_crud_api.APLICACION.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByNameAndBranchId(String name, Integer branchId);

    @Query(value = """
    SELECT p.* FROM product p
    WHERE p.branch_id IN (
        SELECT id FROM branch WHERE franchise_id = :franchiseId
    )
    AND p.stock = (
        SELECT MAX(p2.stock)
        FROM product p2
        WHERE p2.branch_id = p.branch_id
    )
    """, nativeQuery = true)
    List<Product> findTopStockProductsByFranchise(@Param("franchiseId") Integer franchiseId);
}
