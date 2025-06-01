package com.franquicias_crud_api.APLICACION.dto;

public class BranchDTO {
    private Integer id;
    private String name;
    private FranchiseDTO franchise;

    public BranchDTO() {}

    public BranchDTO(Integer id, String name, FranchiseDTO franchise) {
        this.id = id;
        this.name = name;
        this.franchise = franchise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FranchiseDTO getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseDTO franchise) {
        this.franchise = franchise;
    }
}
