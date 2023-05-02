package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.utils.MockUtils;
import aplication.programming.nttdata.dto.ClienteDto;
import aplication.programming.nttdata.services.IClienteServicio;
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
        Mockito.when(clienteServicio.create(Mockito.any()))
                .thenReturn(Mono.just(new ClienteDto()));
        StepVerifier.create(clienteController.registrar(MockUtils.buildClientDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allClient() {
        Mockito.when(clienteServicio.allClient())
                .thenReturn(Flux.just(MockUtils.buildClientDto()));
        StepVerifier.create(clienteController.listar())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(clienteServicio.update(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clienteController.actualizar(1L, MockUtils.buildClientDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(clienteServicio.delete(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clienteController.eliminar(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}