package com.example.product.service;

import com.example.product.dto.ResponseProduct;
import com.example.product.dto.RequestProduct;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductServ {

    List<ResponseProduct> findAll();
    ResponseProduct getById(Integer id);
    boolean existById(Integer id);
    ResponseProduct insert(RequestProduct request);
}
