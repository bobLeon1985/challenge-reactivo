package com.nttdata.controller;

import com.nttdata.dto.CuentaDto;
import com.nttdata.services.ICuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaServicio cuentaServicio;

    @PostMapping
    public Mono<ResponseEntity<CuentaDto>> registrar(@RequestBody CuentaDto request){
        return cuentaServicio.registrar(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<CuentaDto>>> listar(){
        return Mono.just(ResponseEntity.ok().body(cuentaServicio.listar()));
    }

    @PutMapping("/{idCuenta}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idCuenta, @RequestBody CuentaDto request){
        return cuentaServicio.actualizar(idCuenta, request)
                .thenReturn(ResponseEntity.ok().body("Account updated successfully"));
    }

    @DeleteMapping("/{idCuenta}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idCuenta){
        return cuentaServicio.eliminar(idCuenta)
                .thenReturn(ResponseEntity.ok().body("Account deleted successfully"));
    }
}
