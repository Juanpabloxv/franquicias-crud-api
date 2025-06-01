package com.franquicias_crud_api.APLICACION.dto;

public class ProductDTO {
    private Integer id;
    private String name;
    private int stock;
    private BranchDTO branch;

    public ProductDTO() {}

    public ProductDTO(Integer id, String name, int stock, BranchDTO branch) {
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

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }
}
