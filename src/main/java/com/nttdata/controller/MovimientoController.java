package com.nttdata.controller;

import com.nttdata.dto.MovimientoDto;
import com.nttdata.services.IMovimientoServicio;
import com.nttdata.vo.response.MovementResponseVO;
import com.nttdata.vo.response.StatementResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.sql.Date;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Resource
    private IMovimientoServicio movimientoServicio;

    @PostMapping
    public Mono<ResponseEntity<MovementResponseVO>> registrar(@RequestBody MovimientoDto request) {
        return movimientoServicio.registrar(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<MovementResponseVO>>> listar() {
        return Mono.just(ResponseEntity.ok().body(movimientoServicio.listar()));
    }

    @PutMapping("/{idMovimiento}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idMovimiento, @RequestBody MovimientoDto request) {
        return movimientoServicio.actualizar(idMovimiento, request)
                .thenReturn(ResponseEntity.ok().body("Movement updated successfully"));
    }

    @DeleteMapping("/{idMovimiento}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idMovimiento) {
        return movimientoServicio.eliminar(idMovimiento)
                .thenReturn(ResponseEntity.ok().body("Move successfully removed"));
    }

    @GetMapping("/reporte")
    public Mono<ResponseEntity<Flux<StatementResponseVO>>> reporte(@RequestParam Date fechaInicio, @RequestParam Date fechaFin,
                                                                   @RequestParam String identificacion) {
        return Mono.just(ResponseEntity.ok().body(movimientoServicio.reporte(fechaInicio, fechaFin, identificacion)));
    }

}
