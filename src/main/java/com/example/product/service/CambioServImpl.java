package com.example.product.service;

import com.example.product.dto.ResponseTasa;
import com.example.product.product.Cambio;
import com.example.product.repository.RepositoryCambio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@AllArgsConstructor
public class CambioServImpl implements CambioService{
    private RepositoryCambio repository;

    @Override
    public List<ResponseTasa> findAll() {
        List<Cambio> cambios = repository.findAll();

        return !cambios.isEmpty() ? cambios.stream().map(this::cambiosToResponse).collect(Collectors.toList()) : null;
    }

    @Override
    public ResponseTasa getById(Integer id) {
      return  null;
    }

    @Override
    public boolean existById(Integer id) {
       return repository.existsById(id);
    }

    @Override
    public ResponseTasa insert(ResponseTasa request) {
        Cambio cambio =  repository.save(this.requestToCambio(request));
        return this.cambiosToResponse(cambio);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public ResponseTasa updateCambio(ResponseTasa request) {

       Cambio cambio =  repository.save(this.requestToCambio(request));
        return this.cambiosToResponse(cambio);
    }

    ResponseTasa cambiosToResponse(Cambio cambio){

        return new  ResponseTasa(
                cambio.getId(),
                cambio.getNombre(),
                cambio.getTasa()

        );
    }
    Cambio requestToCambio (ResponseTasa request){

        return new  Cambio(
                request.getId(),
                request.getNombre(),
                request.getTasa()

        );
    }

}
