package com.example.product.controller;

import com.example.product.dto.Message;
import com.example.product.dto.ResponseProduct;
import com.example.product.dto.RequestProduct;
import com.example.product.service.ProductServ;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductoController {
    private final ProductServ productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<ResponseProduct>> list(){
        List<ResponseProduct> list = productoService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProduct> getById(@PathVariable("id") Integer id){
        if(!productoService.existById(id))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        ResponseProduct product = productoService.getById(id);
        return new ResponseEntity(product, HttpStatus.OK);
    }
    @PostMapping("/create")
    public  ResponseEntity<ResponseProduct> create(@RequestBody RequestProduct request) {
        ResponseProduct product = productoService.insert(request);
        return new ResponseEntity(product, HttpStatus.OK);
    }
}
