package com.nttdata.services.impl;

import com.nttdata.common.exception.BankError;
import com.nttdata.dto.MovimientoDto;
import com.nttdata.enums.TipoMovimientoEnums;
import com.nttdata.model.Cuenta;
import com.nttdata.model.Movimiento;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.repository.MovimientoRepository;
import com.nttdata.services.IMovimientoServicio;
import com.nttdata.vo.request.BalanceValueVO;
import com.nttdata.vo.response.MovementResponseVO;
import com.nttdata.vo.response.StatementResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

@Slf4j
@Service
public class MovimientoServicioImpl implements IMovimientoServicio {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Mono<MovementResponseVO> registrar(MovimientoDto request) {
        log.info("Creation of movimientos");
        return cuentaRepository.findByNumeroCuentaByTipoCuenta(request.getAccountNumber(), request.getAccountType())
                .flatMap(account -> Mono.zip(
                                        movimientoRepository.getBalance(account.getIdCuenta(), "D").switchIfEmpty(Mono.just(0.00)),
                                        movimientoRepository.getBalance(account.getIdCuenta(), "R").switchIfEmpty(Mono.just(0.00))
                                ).map(values -> account.getSaldoInicial() + values.getT1() - values.getT2())
                                .flatMap(balance -> {
                                    if (request.getMovementType().equals("R") && (balance - request.getValue()) < 0) {
                                        return Mono.error(BankError.NTT010);
                                    }
                                    return Mono.just(balance);
                                })
                                .flatMap(saldoInicial -> {
                                    Movimiento movimiento = new Movimiento();
                                    movimiento.setFecha(request.getDate().toLocalDate());
                                    movimiento.setTipoMovimiento(TipoMovimientoEnums.valueOf(request.getMovementType()));
                                    movimiento.setValor(request.getValue());
                                    movimiento.setIdCuenta(account.getIdCuenta());
                                    movimiento.setSaldo(request.getMovementType().equals("D") ?
                                            saldoInicial + request.getValue() : saldoInicial - request.getValue());
                                    return movimientoRepository.save(movimiento)
                                            .onErrorResume(error -> {
                                                log.error("Error {}", error.getMessage());
                                                return Mono.error(BankError.NTT011);
                                            });
                                }).map(movement -> {
                                    MovementResponseVO movementResponseVO = new MovementResponseVO();
                                    movementResponseVO.setAccountNumber(account.getNumeroCuenta());
                                    movementResponseVO.setAccountType(account.getTipoCuenta().getDescripcion());
                                    movementResponseVO.setDate(Date.valueOf(movement.getFecha()));
                                    movementResponseVO.setMovementType(movement.getTipoMovimiento().getDescripcion());
                                    movementResponseVO.setValue(movement.getValor());
                                    movementResponseVO.setBalance(movement.getSaldo());
                                    movementResponseVO.setStatus(account.getEstado());
                                    return movementResponseVO;
                                })
                )
                .doOnSuccess(success -> log.info("Movement created successfully"));
    }

    @Override
    public Flux<MovementResponseVO> listar() {
        return movimientoRepository.findAll()
                .flatMap(movement -> cuentaRepository.findById(movement.getIdMovimiento())
                        .map(account -> {
                            MovementResponseVO movementResponseVO = new MovementResponseVO();
                            movementResponseVO.setAccountNumber(account.getNumeroCuenta());
                            movementResponseVO.setAccountType(account.getTipoCuenta().getDescripcion());
                            movementResponseVO.setDate(Date.valueOf(movement.getFecha()));
                            movementResponseVO.setMovementType(String.valueOf(movement.getTipoMovimiento()));
                            movementResponseVO.setValue(movement.getValor());
                            movementResponseVO.setBalance(movement.getSaldo());
                            movementResponseVO.setStatus(account.getEstado());
                            return movementResponseVO;
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> actualizar(Long idMovimiento, MovimientoDto request) {
        return cuentaRepository.findByNumeroCuentaByTipoCuenta(request.getAccountNumber(), request.getAccountType())
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
                .doOnSuccess(success -> log.info("Movement updated successfully"));
    }

    @Override
    @Transactional
    public Mono<Void> eliminar(Long idMovimiento) {
        return movimientoRepository.deleteById(idMovimiento)
                .doOnSuccess(success -> log.info("Move successfully removed"));

    }

    @Override
    public Flux<StatementResponseVO> reporte(Date dateStart, Date dateEnd, String identification) {
        return clienteRepository.findByIdentificacion(identification)
                .flatMapMany(client -> cuentaRepository.findCuentaByIdCliente(client.getIdCliente())
                        .onErrorResume(error -> {
                            log.error("An error occurred while consulting the client's account. Detail = {}", error.getMessage());
                                return Mono.error(BankError.NTT012);
                        })
                        .flatMap(account -> processValues(account, dateStart, dateEnd)
                                .map(values -> {
                                    StatementResponseVO statementResponseVO = new StatementResponseVO();
                                    statementResponseVO.setClient(client.getNombre());
                                    statementResponseVO.setAccountNumber(account.getNumeroCuenta());
                                    statementResponseVO.setAccountType(account.getTipoCuenta().getDescripcion());
                                    statementResponseVO.setInitialBalance(values.getInitialBalance());
                                    statementResponseVO.setTotalCredits(values.getTotalCredits());
                                    statementResponseVO.setTotalDebits(values.getTotalDebits());
                                    statementResponseVO.setBalance(values.getBalance());
                                    statementResponseVO.setStatus(account.getEstado());
                                    return statementResponseVO;
                                })
                        )
                );
    }

    private Mono<BalanceValueVO> processValues(Cuenta account, Date dateStart, Date dateEnd) {
        return getInitialBalance(account, dateStart)
                .flatMap(initialBalance -> Mono.zip(
                                getBalance(account, dateStart, dateEnd, initialBalance),
                                movimientoRepository.getBalanceByDate(account.getIdCuenta(), "Deposito", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                                movimientoRepository.getBalanceByDate(account.getIdCuenta(), "Retiro", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
                        )
                        .map(values -> {
                            BalanceValueVO balanceValueVO = new BalanceValueVO();
                            balanceValueVO.setInitialBalance(initialBalance);
                            balanceValueVO.setTotalCredits(values.getT2());
                            balanceValueVO.setTotalDebits(values.getT3());
                            balanceValueVO.setBalance(values.getT1());
                            return balanceValueVO;
                        }));
    }

    private Mono<Double> getInitialBalance(Cuenta account, Date dateStart) {
        return Mono.zip(
                        movimientoRepository.getBalancePrevious(account.getIdCuenta(), "Deposito", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00)),
                        movimientoRepository.getBalancePrevious(account.getIdCliente(), "Retiro", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> account.getSaldoInicial() + values.getT1() - values.getT2())
                .switchIfEmpty(Mono.just(0.00));
    }

    private Mono<Double> getBalance(Cuenta account, Date dateStart, Date dateEnd, Double initialBalance) {
        return Mono.zip(
                        movimientoRepository.getBalanceByDate(account.getIdCuenta(), "Deposito", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                        movimientoRepository.getBalanceByDate(account.getIdCuenta(), "Retiro", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> initialBalance + values.getT1() - values.getT2())
                .switchIfEmpty(Mono.just(0.00));
    }
}
