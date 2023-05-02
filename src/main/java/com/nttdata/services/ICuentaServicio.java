package com.nttdata.services;

import com.nttdata.dto.CuentaDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface ICuentaServicio {

    Mono<CuentaDto> registrar(CuentaDto request);

    Flux<CuentaDto> listar();

    Mono<Void> actualizar(Long idAccount, CuentaDto request);

    Mono<Void> eliminar(Long idAccount);
}
