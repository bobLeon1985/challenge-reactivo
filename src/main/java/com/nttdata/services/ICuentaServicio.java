package com.nttdata.services;

import com.nttdata.dto.CuentaDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface ICuentaServicio {

    Mono<CuentaDto> registrar(CuentaDto request);

    Flux<CuentaDto> buscar();

    Mono<CuentaDto> buxcarXId (Long id);

    Mono<Void> actualizar(CuentaDto request);

    Mono<Void> eliminar(Long idAccount);
}
