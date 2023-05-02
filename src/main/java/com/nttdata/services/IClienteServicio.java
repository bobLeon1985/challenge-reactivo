package com.nttdata.services;

import com.nttdata.dto.ClienteDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface IClienteServicio {

    Mono<ClienteDto> registrar(ClienteDto request);

    Flux<ClienteDto> listar();

    Mono<Void> actualizar(Long idClient, ClienteDto request);

    Mono<Void> eliminar(Long idClient);
}
