package aplication.programming.nttdata.repository;

import aplication.programming.nttdata.model.Movimiento;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
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
}
