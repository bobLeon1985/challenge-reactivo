package com.nttdata.controller;

import com.nttdata.utils.MockUtils;
import com.nttdata.dto.ClienteDto;
import com.nttdata.services.IClienteServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(classes = {ClientControllerTest.class})
class ClientControllerTest {

    @Mock
    private IClienteServicio clienteServicio;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void create() {
        Mockito.when(clienteServicio.registrar(Mockito.any()))
                .thenReturn(Mono.just(new ClienteDto()));
        StepVerifier.create(clienteController.registrar(MockUtils.buildClientDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allClient() {
        Mockito.when(clienteServicio.buscar())
                .thenReturn(Flux.just(MockUtils.buildClientDto()));
        StepVerifier.create(clienteController.buscar())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(clienteServicio.actualizar(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clienteController.actualizar(1L, MockUtils.buildClientDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(clienteServicio.eliminar(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clienteController.eliminar(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}