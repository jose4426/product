package com.example.product.service;

import com.example.product.dto.ResponseTasa;
import com.example.product.product.Cambio;
import com.example.product.repository.RepositoryCambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class CambioServImpl implements CambioService{
    @Autowired(required=true)
    private RepositoryCambio repository;

    @Autowired(required=true)
    private WebClient webClient;


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
    public ResponseTasa insert(ResponseTasa request, String token) {
        if (validateToken(token)) {
            Cambio cambio = repository.save(this.requestToCambio(request));
            return this.cambiosToResponse(cambio);
        } throw new RuntimeException("no tines los permisos");
    }

    @Override
    public void delete(Integer id,String token) {
        if (validateToken(token)) {

            repository.deleteById(id);
        }
    }

    @Override
    public ResponseTasa updateCambio(ResponseTasa request) {
        if (Objects.isNull(request.getId())){
            throw new IllegalArgumentException("el id no puede ser null");
        }

            Cambio cambioExist = repository.findById(request.getId()).orElse(null);
            cambioExist.setTasa(request.getTasa());
            cambioExist.setNombre(request.getNombre());

            Cambio cambio = repository.save(cambioExist);

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
    public boolean validateToken(String token) {
        return webClient.get()
                .uri("http://localhost:8080/api/user/validate")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
