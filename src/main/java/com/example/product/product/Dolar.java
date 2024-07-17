package com.example.product.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dolar")
public class Dolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "valor")
    private double valor;

    public Dolar(String moneda, double valor) {
        this.moneda = moneda;
        this.valor = valor;
    }
}
