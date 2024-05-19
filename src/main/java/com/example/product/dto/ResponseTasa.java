package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseTasa {
    private int id;
    private String nombre;
    private float tasa;
}
