package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.dto.ClienteDto;
import aplication.programming.nttdata.model.Cliente;
import aplication.programming.nttdata.services.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteServicio iClienteServicio;

    @PostMapping
    public Mono<ResponseEntity<ClienteDto>> registrar(@RequestBody ClienteDto request){
        return iClienteServicio.create(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<ClienteDto>>> listar(){
        return Mono.just(ResponseEntity.ok().body(iClienteServicio.allClient()));
    }

    @PutMapping("/{idClient}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idClient, @RequestBody ClienteDto request){
        return iClienteServicio.update(idClient, request)
                .thenReturn(ResponseEntity.ok().body("User updated successfully"));
    }

    @DeleteMapping("/{idClient}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idClient){
        return iClienteServicio.delete(idClient)
                .thenReturn(ResponseEntity.ok().body("User deleted successfully"));
    }
}
