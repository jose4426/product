package com.example.product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RequestProduct {

    private int id;
    private String nombre;
    private double precio;
}
