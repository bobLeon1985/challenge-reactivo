package com.nttdata.controller;

import com.nttdata.dto.ClienteDto;
import com.nttdata.services.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteServicio clienteServicio;

    @PostMapping
    public Mono<ResponseEntity<ClienteDto>> registrar(@RequestBody ClienteDto request){
        return clienteServicio.create(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<ClienteDto>>> listar(){
        return Mono.just(ResponseEntity.ok().body(clienteServicio.allClient()));
    }

    @PutMapping("/{idClient}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idClient, @RequestBody ClienteDto request){
        return clienteServicio.update(idClient, request)
                .thenReturn(ResponseEntity.ok().body("User updated successfully"));
    }

    @DeleteMapping("/{idClient}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idClient){
        return clienteServicio.delete(idClient)
                .thenReturn(ResponseEntity.ok().body("User deleted successfully"));
    }
}
