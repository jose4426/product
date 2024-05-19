package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestProduct {

    private int id;
    private String nombre;
    private float precio;
}
