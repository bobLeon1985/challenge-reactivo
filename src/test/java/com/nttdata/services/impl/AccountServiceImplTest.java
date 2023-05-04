package com.nttdata.services.impl;

import com.nttdata.common.exception.BankMessageError;
import com.nttdata.mapper.Mapper;
import com.nttdata.utils.MockUtils;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@ContextConfiguration(classes = {AccountServiceImplTest.class})
class AccountServiceImplTest {

    @InjectMocks
    private CuentaServicioImpl cuentaServicio;
    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @MockBean
    Mapper mapper;

    @BeforeEach
    void setupInit(){
        Mockito.when(mapper.cuentaDTotoCuenta(Mockito.any(), Mockito.anyLong())).thenReturn(MockUtils.buildAccount());
        Mockito.when(mapper.cuentaToCuentaDto(Mockito.any(), Mockito.anyString())).thenReturn(MockUtils.buildCuentaDtoResponse());
    }
    @Test
    void create() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.save(any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        StepVerifier.create(cuentaServicio.registrar(MockUtils.buildCuentaDto()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankMessageError.NTT002));
        StepVerifier.create(cuentaServicio.registrar(MockUtils.buildCuentaDto()))
                .expectError()
                .verify();
    }


    @Test
    void allAccount() {
        Mockito.when(cuentaRepository.findAll()).thenReturn(Flux.just(MockUtils.buildAccountAll()));
        Mockito.when(clienteRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(MockUtils.buildClient()));
        StepVerifier.create(cuentaServicio.buscar())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(cuentaRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(MockUtils.buildAccountAll()));
        Mockito.when(cuentaRepository.save(Mockito.any())).thenReturn(Mono.empty());
        StepVerifier.create(cuentaServicio.actualizar( MockUtils.buildCuentaDtoUpdate()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(cuentaRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(cuentaServicio.eliminar(1L))
                .expectComplete()
                .verify();
    }

}