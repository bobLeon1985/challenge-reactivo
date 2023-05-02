package com.nttdata.services.impl;

import com.nttdata.utils.MockUtils;
import com.nttdata.common.exception.BankError;
import com.nttdata.repository.ClienteRepository;
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


@SpringBootTest
@ContextConfiguration(classes = {ClientServiceImplTest.class})
class ClientServiceImplTest {
/*
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteServicioImpl clientService;

    @Test
    void create(){
        Mockito.when(clienteRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        StepVerifier.create(clientService.create(MockUtils.buildClientDto()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail(){
        Mockito.when(clienteRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT001));
        StepVerifier.create(clientService.create(MockUtils.buildClientDto()))
                .expectError()
                .verify();
    }

    @Test
    void allClient() {
        Mockito.when(clienteRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildClient()));
        StepVerifier.create(clientService.allClient())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void allClientFail() {
        Mockito.when(clienteRepository.findAll())
                .thenReturn(Flux.error(BankError.NTT007));
        StepVerifier.create(clientService.allClient())
                .expectError()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(clienteRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientService.update(1L, MockUtils.buildClientDto()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(clienteRepository.save(Mockito.any()))
                .thenReturn(Mono.error(BankError.NTT008));
        StepVerifier.create(clientService.update(1L, MockUtils.buildClientDto()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(clienteRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientService.delete(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteFail() {
        Mockito.when(clienteRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.error(BankError.NTT009));
        StepVerifier.create(clientService.delete(1L))
                .expectError()
                .verify();
    }*/
}