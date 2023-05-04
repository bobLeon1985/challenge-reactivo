package com.nttdata.repository;

import com.nttdata.model.Movimiento;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Repository
public interface MovimientoRepository extends ReactiveCrudRepository<Movimiento, Long> {

    @Query("select sum(m.valor) from movimiento m where m.fk_cuenta = :idAccount and m.tipo_movimiento = :movementType")
    Mono<Double> getBalance(Long idAccount, String movementType);
    @Query("select sum(m.valor) from movimiento m where m.fk_cuenta = :idAccount and m.tipo_movimiento = :movementType and m.fecha < :dateStart")
    Mono<Double> getBalancePrevious(Long idAccount, String movementType, LocalDate dateStart);
    @Query("select sum(m.valor) from movimiento m where m.fk_cuenta = :idAccount and m.tipo_movimiento = :movementType and m.fecha between :dateStart and :dateEnd")
    Mono<Double> getBalanceByDate(Long idAccount, String movementType, LocalDate dateStart, LocalDate dateEnd);

    @Query("select sum(m.valor) * -1 from movimiento m \n" +
            "inner join cuenta cm on cm.id_cuenta  = m.fk_cuenta  \n" +
            "inner join cuenta c on c.id_cuenta = cm.id_cuenta \n" +
            "where to_date(cast(m.fecha as text), 'YYYY-MM-DD') >= :fecha and m.tipo_movimiento = :tipoMovimiento \n" +
            "and c.id_cuenta = :idAccount group by c.id_cuenta ")
    Mono<Double> getBalanceByToDate(Long idAccount, LocalDate fecha, String tipoMovimiento);

    Flux<Movimiento> findByIdCuenta(Long fkCuenta);
}
