package com.franquicias_crud_api.APLICACION.model;
import jakarta.persistence.*;

@Entity
@Table(
        name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "branch_id"})
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public Product() {
    }

    public Product(String name, int stock, Branch branch) {
        this.name = name;
        this.stock = stock;
        this.branch = branch;
    }

    public Product(int id, String name, int stock, Branch branch) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branch = branch;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
