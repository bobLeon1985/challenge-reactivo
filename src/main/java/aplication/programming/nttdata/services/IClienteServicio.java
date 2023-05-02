package aplication.programming.nttdata.services;

import aplication.programming.nttdata.dto.ClienteDto;
import aplication.programming.nttdata.model.Cliente;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface IClienteServicio {

    Mono<ClienteDto> create(ClienteDto request);

    Flux<ClienteDto> allClient();

    Mono<Void> update(Long idClient, ClienteDto request);

    Mono<Void> delete(Long idClient);
}
