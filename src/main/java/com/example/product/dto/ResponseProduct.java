package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseProduct {

    private int id;
    private String nombre;
    private double precio;

}
