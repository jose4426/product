package com.example.product.service;

import com.example.product.dto.RequestProduct;
import com.example.product.dto.ResponseProduct;
import com.example.product.dto.ResponseTasa;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CambioService {
    List<ResponseTasa> findAll( );
    ResponseTasa getById(Integer id);
    boolean existById(Integer id);
    ResponseTasa insert(ResponseTasa request, String token);
    void delete(Integer id, String token);
    ResponseTasa updateCambio(ResponseTasa request,String token);
}
