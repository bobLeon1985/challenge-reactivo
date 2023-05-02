package com.nttdata.services.impl;

import com.nttdata.utils.MockUtils;
import com.nttdata.common.exception.BankError;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.repository.MovimientoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration(classes = {MovementServiceImplTest.class})
class MovementServiceImplTest {
/*
    @Mock
    private MovimientoRepository movimientoRepository;
    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private MovimientoServicioImpl movementService;

    @Test
    void create() {
        Mockito.when(cuentaRepository.findByNumeroCuentaByTipoCuenta(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movimientoRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movimientoRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildMovement()));
        StepVerifier.create(movementService.registrar(MockUtils.buildMovimientoDto()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail() {
        Mockito.when(cuentaRepository.findByNumeroCuentaByTipoCuenta(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccountFail()));
        Mockito.when(movimientoRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(800.00));
        Mockito.when(movimientoRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildMovement()));
        StepVerifier.create(movementService.registrar(MockUtils.buildMovimientoDto()))
                .expectError()
                .verify();
    }

    @Test
    void createFailSave() {
        Mockito.when(cuentaRepository.findByNumeroCuentaByTipoCuenta(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movimientoRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(800.00));
        Mockito.when(movimientoRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT011));
        StepVerifier.create(movementService.registrar(MockUtils.buildMovimientoDto()))
                .expectError()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(movimientoRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildMovement()));
        Mockito.when(cuentaRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        StepVerifier.create(movementService.listar())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(cuentaRepository.findByNumeroCuentaByTipoCuenta(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movimientoRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movimientoRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.actualizar(1L, MockUtils.buildMovimientoDto()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(cuentaRepository.findByNumeroCuentaByTipoCuenta(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccountFail()));
        Mockito.when(movimientoRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movimientoRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.actualizar(1L, MockUtils.buildMovimientoDto()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(movimientoRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.eliminar(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void report() {
        Mockito.when(clienteRepository.findByIdentificacion(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.findCuentaByIdCliente(Mockito.any()))
                .thenReturn(Flux.just(MockUtils.buildAccount()));
        Mockito.when(movimientoRepository.getBalanceByDate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movimientoRepository.getBalancePrevious(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        StepVerifier.create(movementService.reporte(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void reportFail() {
        Mockito.when(clienteRepository.findByIdentificacion(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.findCuentaByIdCliente(Mockito.any()))
                .thenReturn(Flux.error(BankError.NTT012));
        Mockito.when(movimientoRepository.getBalanceByDate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movimientoRepository.getBalancePrevious(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        StepVerifier.create(movementService.reporte(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .expectError()
                .verify();
    }*/
}