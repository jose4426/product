package com.example.product.service;

import com.example.product.dto.ResponseProduct;
import com.example.product.dto.RequestProduct;
import com.example.product.product.Product;
import com.example.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProducServImpl implements ProductServ {

    private ProductRepository repository;
    @Override
    public List<ResponseProduct> findAll() {
        List<Product> product = new ArrayList<>();
        product =  repository.findAll();

        return !product.isEmpty() ? product.stream().map(this::entityToResponse).collect(Collectors.toList()) : null;
    }

    @Override
    public ResponseProduct getById(Integer id) {
       Product product = repository.findById(id).get();

        return entityToResponse(product);
    }

    @Override
    public boolean existById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public ResponseProduct insert(RequestProduct request) {
        Product product = requestToEntity(request);

        ResponseProduct response = entityToResponse(repository.save(product));

        return response;
    }

    private ResponseProduct entityToResponse(Product product){

        return new ResponseProduct(
                product.getId(),
                product.getNombre(),
                product.getPrecio()

        );
    }
    private Product requestToEntity(RequestProduct request){

        return new Product(
                request.getId(),
                request.getNombre(),
                request.getPrecio()

        );
    }

}

