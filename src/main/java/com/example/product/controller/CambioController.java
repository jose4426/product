package com.example.product.controller;

import com.example.product.dto.Message;
import com.example.product.dto.ResponseProduct;
import com.example.product.dto.ResponseTasa;
import com.example.product.service.CambioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cambio")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CambioController {
    private  final CambioService service;

    @GetMapping("/lista")
    public ResponseEntity<List<ResponseTasa>> list() {
        List<ResponseTasa> list = service.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProduct> getById(@PathVariable("id") Integer id){
        if(!service.existById(id))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        ResponseTasa product = service.getById(id);
        return new ResponseEntity(product, HttpStatus.OK);
    }
    @PutMapping("/update/{request}")
    public  ResponseEntity<ResponseTasa> update(@RequestBody ResponseTasa request,@RequestHeader( value ="Authorization") String token) {
        ResponseTasa product = service.updateCambio(request,token);
        return new ResponseEntity(product, HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseProduct> deleteById(@PathVariable("id") Integer id,@RequestHeader( value ="Authorization") String token){
        if(!service.existById(id))
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
         service.delete(id,token);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/create")
    public  ResponseEntity<ResponseTasa> insert(@RequestBody ResponseTasa request,@RequestHeader( value ="Authorization") String token) {
        ResponseTasa product = service.insert(request, token);
        return new ResponseEntity(product, HttpStatus.OK);
    }

}
