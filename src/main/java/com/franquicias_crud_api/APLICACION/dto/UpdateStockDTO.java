package com.franquicias_crud_api.APLICACION.dto;

public class UpdateStockDTO {
    private Integer stock;

    public UpdateStockDTO() {}

    public UpdateStockDTO(Integer stock) {
        this.stock = stock;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
