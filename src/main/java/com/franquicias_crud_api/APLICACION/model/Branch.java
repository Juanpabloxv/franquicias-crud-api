package com.franquicias_crud_api.APLICACION.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Branch() {
    }

    public Branch(String name, Franchise franchise, List<Product> products) {
        this.name = name;
        this.franchise = franchise;
        this.products = products;
    }

    public Branch(int id, String name, Franchise franchise, List<Product> products) {
        this.id = id;
        this.name = name;
        this.franchise = franchise;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
