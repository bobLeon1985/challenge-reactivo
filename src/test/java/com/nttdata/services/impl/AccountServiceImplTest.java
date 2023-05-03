package com.nttdata.services.impl;

import com.nttdata.mapper.Mapper;
import com.nttdata.model.Cuenta;
import com.nttdata.utils.MockUtils;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;


@SpringBootTest
@ContextConfiguration(classes = {AccountServiceImplTest.class})
class AccountServiceImplTest {

    @InjectMocks
    private CuentaServicioImpl accountService;
    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @MockBean
    private Mapper mapper;
    @Test
    void create() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(mapper.cuentaDTotoCuenta(Mockito.any(), Mockito.anyLong())).thenReturn(MockUtils.buildAccount());
        Mockito.when(cuentaRepository.save(any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(mapper.cuentaToCuentaDto(Mockito.any(), Mockito.anyString())).thenReturn(MockUtils.buildCuentaDtoResponse());
        StepVerifier.create(accountService.registrar(MockUtils.buildCuentaDto()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }
/*
    @Test
    void createFail() {
        Mockito.when(clienteRepository.findByIdentificacion(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT004));
        StepVerifier.create(accountService.registrar(MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void createFailClient() {
        Mockito.when(clienteRepository.findByIdentificacion(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT004));
        StepVerifier.create(accountService.registrar(MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(cuentaRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildAccount()));
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        StepVerifier.create(accountService.listar())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void allAccountFail() {
        Mockito.when(cuentaRepository.findAll())
                .thenReturn(Flux.error(BankError.NTT004));
        StepVerifier.create(accountService.listar())
                .expectError()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(cuentaRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountService.actualizar(1L, MockUtils.buildAccountRequestVO()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(cuentaRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT004));
        StepVerifier.create(accountService.actualizar(1L, MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(cuentaRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountService.eliminar(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteFail() {
        Mockito.when(cuentaRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.error(BankError.NTT004));
        StepVerifier.create(accountService.eliminar(1L))
                .expectError()
                .verify();
    }
*/
}