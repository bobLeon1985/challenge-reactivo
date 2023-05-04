package com.nttdata.services.impl;

import com.nttdata.common.exception.BankMessageError;
import com.nttdata.dto.MovimientoDto;
import com.nttdata.enums.TipoMovimientoEnums;
import com.nttdata.mapper.Mapper;
import com.nttdata.model.Movimiento;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.repository.MovimientoRepository;
import com.nttdata.services.IMovimientoServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MovimientoServicioImpl implements IMovimientoServicio {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Value("${limite.retiro}")
    private Double LIMITE_DIARIO_RETIRO;

    @Override
    @Transactional
    public Mono<MovimientoDto> registrarMovimiento(MovimientoDto request) {
        log.info("Inicio el proceso de creacion de movimientos");
        return cuentaRepository.findById(request.getIdCuenta())
                .flatMap(cuenta -> Mono.zip(
                                        movimientoRepository.getBalance(cuenta.getIdCuenta(), TipoMovimientoEnums.D.name()).switchIfEmpty(Mono.just(0.00)),
                                        movimientoRepository.getBalance(cuenta.getIdCuenta(), TipoMovimientoEnums.R.name()).switchIfEmpty(Mono.just(0.00))
                                )
                                .map(values -> cuenta.getSaldoInicial() + values.getT1() - (values.getT2() * -1))
                                .flatMap(balance -> {
                                    if (request.getTipoMovimiento().equals("R") && (balance - request.getValor()) < 0) {
                                        return Mono.error(BankMessageError.NTT010);
                                    }
                                    return Mono.just(balance);
                                })
                                .flatMap(aDouble -> movimientoRepository.getBalanceByToDate(request.getIdCuenta(), request.getFecha().toLocalDate(), request.getTipoMovimiento())
                                        .flatMap(movimientoDia -> {
                                            if (movimientoDia >= LIMITE_DIARIO_RETIRO)
                                                return Mono.error(BankMessageError.NTT013);
                                            else
                                                return Mono.just(aDouble);
                                        })
                                )
                                .flatMap(saldoInicial -> {
                                    Movimiento movimiento = new Movimiento();
                                    movimiento.setFecha(request.getFecha().toLocalDate());
                                    movimiento.setTipoMovimiento(TipoMovimientoEnums.valueOf(request.getTipoMovimiento()));
                                    movimiento.setValor(request.getTipoMovimiento().equals("R") ? request.getValor() * -1 : request.getValor());
                                    movimiento.setIdCuenta(cuenta.getIdCuenta());
                                    movimiento.setSaldo(request.getTipoMovimiento().equals("D") ? saldoInicial + request.getValor() : saldoInicial - request.getValor());
                                    return movimientoRepository.save(movimiento)
                                            .onErrorResume(error -> {
                                                log.error("Error {}", error.getMessage());
                                                return Mono.error(BankMessageError.NTT010);
                                            });
                                }).map(movimiento ->
                                        Mapper.INSTANCE.movimientoToMoviemientoDto(movimiento, cuenta.getNumeroCuenta(), cuenta.getTipoCuenta().getDescripcion())
                                )

                )
                .doOnSuccess(success -> log.info("Movement created successfully"));
    }

    @Override
    public Flux<MovimientoDto> buscar() {
        return movimientoRepository.findAll()
                .flatMap(movimiento -> {
                    log.info("Movimeinto ={}", movimiento);
                    return cuentaRepository.findById(movimiento.getIdCuenta())
                            .map(cuenta ->
                                    Mapper.INSTANCE.movimientoToMoviemientoDto(movimiento, cuenta.getNumeroCuenta(), cuenta.getTipoCuenta().getDescripcion())
                            );
                });
    }

    @Override
    public Flux<MovimientoDto> buscarXCuenta(Long fkCuenta) {
        return movimientoRepository.findByIdCuenta(fkCuenta)
                .flatMap(movimiento -> cuentaRepository.findById(movimiento.getIdCuenta())
                        .map(cuenta -> Mapper.INSTANCE.movimientoToMoviemientoDto(movimiento, cuenta.getNumeroCuenta(), cuenta.getTipoCuenta().getDescripcion()))
                );
    }


    @Override
    @Transactional
    public Mono<Void> actualizar(Long idMovimiento, MovimientoDto request) {
       /* return cuentaRepository.findByNumeroCuentaByTipoCuenta(request.getAccountNumber(), request.getAccountType())
                .flatMap(account -> Mono.zip(
                                movimientoRepository.getBalance(account.getIdCuenta(), "Deposito").switchIfEmpty(Mono.just(0.00)),
                                movimientoRepository.getBalance(account.getIdCuenta(), "Retiro").switchIfEmpty(Mono.just(0.00))
                        ).map(values -> account.getSaldoInicial() + values.getT1() - values.getT2())
                        .flatMap(balance -> {
                            if (request.getMovementType().equals("Retiro") && (balance - request.getValue()) < 0) {
                                log.error("Insufficient balance to carry out the transaction");
                                return Mono.error(BankError.NTT010);
                            }
                            return Mono.just(balance);
                        })
                        .flatMap(balance -> {
                            Movimiento movement = new Movimiento();
                            movement.setIdMovimiento(idMovimiento);
                            movement.setFecha(request.getDate().toLocalDate());
                            movement.setTipoMovimiento(TipoMovimientoEnums.valueOf(request.getMovementType()));
                            movement.setValor(request.getValue());
                            movement.setIdCuenta(account.getIdCuenta());
                            movement.setSaldo(request.getMovementType().equals("Deposito") ?
                                    balance + request.getValue() : balance - request.getValue());

                            return movimientoRepository.save(movement)
                                    .then();
                        }))
                .doOnSuccess(success -> log.info("Movement updated successfully"));*/
        return null;
    }

    @Override
    @Transactional
    public Mono<Boolean> eliminar(Long idMovimiento) {
        return movimientoRepository.findById(idMovimiento)
                .flatMap(movimiento -> movimientoRepository.deleteById(idMovimiento).thenReturn(Boolean.TRUE))
                .doOnSuccess(success -> log.info("Move successfully removed"));

    }


}
