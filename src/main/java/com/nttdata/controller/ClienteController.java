package com.nttdata.controller;

import com.nttdata.dto.ClienteDto;
import com.nttdata.services.IClienteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {


    private final IClienteServicio clienteServicio;


    @PostMapping
    public Mono<ResponseEntity<ClienteDto>> registrar(@RequestBody ClienteDto request) {
        return clienteServicio.registrar(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<ClienteDto>>> listar() {
        return Mono.just(ResponseEntity.ok().body(clienteServicio.listar()));
    }

    @PutMapping("/{idCliente}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idCliente, @RequestBody ClienteDto request) {
        return clienteServicio.actualizar(idCliente, request)
                .thenReturn(ResponseEntity.ok().body("User updated successfully"));
    }

    @DeleteMapping("/{idClient}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idCliente) {
        return clienteServicio.eliminar(idCliente)
                .thenReturn(ResponseEntity.ok().body("User deleted successfully"));
    }

    public ClienteController(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }
}
