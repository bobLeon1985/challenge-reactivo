package com.nttdata.controller;

import com.nttdata.dto.MovimientoDto;
import com.nttdata.services.IMovimientoServicio;
import com.nttdata.services.IReporteServicio;
import com.nttdata.vo.response.StatementResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private IMovimientoServicio movimientoServicio;

    @Autowired
    private IReporteServicio ireporteServicio;
    @PostMapping
    public Mono<ResponseEntity<MovimientoDto>> registrar(@RequestBody MovimientoDto request) {
        return movimientoServicio.registrarMovimiento(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<MovimientoDto>>> buscar() {
        return Mono.just(ResponseEntity.ok().body(movimientoServicio.buscar()));
    }

    @GetMapping("/{fkCuenta}")
    public Mono<ResponseEntity<Flux<MovimientoDto>>> buscarXCuenta(@PathVariable("fkCuenta") Long fkCuenta) {
        return Mono.just(ResponseEntity.ok().body(movimientoServicio.buscarXCuenta(fkCuenta)));
    }

    @PutMapping("/{idMovimiento}")
    public Mono<ResponseEntity<String>> actualizar(@PathVariable Long idMovimiento, @RequestBody MovimientoDto request) {
        return movimientoServicio.actualizar(idMovimiento, request)
                .thenReturn(ResponseEntity.ok().body("Movement updated successfully"));
    }

    @DeleteMapping("/{idMovimiento}")
    public Mono<ResponseEntity<String>> eliminar(@PathVariable Long idMovimiento) {
        return movimientoServicio.eliminar(idMovimiento)
                .map(aBoolean -> ResponseEntity.ok().body("Move successfully removed"))
                .defaultIfEmpty(new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/reporte")
    public Mono<ResponseEntity<Flux<StatementResponseVO>>> reporte(@RequestParam Date fechaInicio, @RequestParam Date fechaFin,
                                                                   @RequestParam String identificacion) {
        return Mono.just(ResponseEntity.ok().body(ireporteServicio.reporte(fechaInicio, fechaFin, identificacion)));
    }

}
