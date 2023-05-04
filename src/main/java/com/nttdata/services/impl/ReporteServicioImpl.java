package com.nttdata.services.impl;

import com.nttdata.common.exception.BankMessageError;
import com.nttdata.model.Cuenta;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.MovimientoRepository;
import com.nttdata.services.IReporteServicio;
import com.nttdata.vo.request.BalanceValueVO;
import com.nttdata.vo.response.StatementResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

@Slf4j
@Service
public class ReporteServicioImpl implements IReporteServicio {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Flux<StatementResponseVO> reporte(Date dateStart, Date dateEnd, String identification) {
        return clienteRepository.findByIdentificacion(identification)
                .flatMapMany(client -> cuentaRepository.findCuentaByIdCliente(client.getIdCliente())
                        .onErrorResume(error -> {
                            log.error("An error occurred while consulting the client's account. Detail = {}", error.getMessage());
                            return Mono.error(BankMessageError.NTT001);
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
                                movimientoRepository.getBalanceByDate(account.getIdCuenta(), "D", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                                movimientoRepository.getBalanceByDate(account.getIdCuenta(), "R", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
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
                        movimientoRepository.getBalancePrevious(account.getIdCuenta(), "D", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00)),
                        movimientoRepository.getBalancePrevious(account.getIdCliente(), "R", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> account.getSaldoInicial() + values.getT1() - values.getT2() * -1)
                .switchIfEmpty(Mono.just(0.00));
    }

    private Mono<Double> getBalance(Cuenta account, Date dateStart, Date dateEnd, Double initialBalance) {
        return Mono.zip(
                        movimientoRepository.getBalanceByDate(account.getIdCuenta(), "D", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                        movimientoRepository.getBalanceByDate(account.getIdCuenta(), "R", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> initialBalance + values.getT1() - values.getT2() * -1)
                .switchIfEmpty(Mono.just(0.00));
    }
}
