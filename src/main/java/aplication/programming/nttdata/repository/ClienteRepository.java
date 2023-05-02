package aplication.programming.nttdata.repository;

import aplication.programming.nttdata.model.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<Cliente, Long> {

    Mono <Cliente> findByIdCliente (Long idCliente);
    Mono<Cliente> findByIdentificacion(String identification);
}
