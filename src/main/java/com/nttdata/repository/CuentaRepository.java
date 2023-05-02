package com.nttdata.repository;

import com.nttdata.model.Cuenta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CuentaRepository extends ReactiveCrudRepository<Cuenta, Long> {

    @Query("select a.* from cuenta a where a.numero_cuenta = :accountNumber and a.tipo_cuenta = :accountType")
    Mono<Cuenta> findByNumeroCuentaByTipoCuenta(String accountNumber, String accountType);
    Flux<Cuenta> findCuentaByIdCliente(Long idClient);
}
