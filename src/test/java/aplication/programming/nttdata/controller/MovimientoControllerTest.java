package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.utils.MockUtils;
import aplication.programming.nttdata.services.IMovimientoServicio;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
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

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration(classes = {MovimientoControllerTest.class})
class MovimientoControllerTest {

    @Mock
    private IMovimientoServicio movementService;

    @InjectMocks
    private MovimientoController movimientoController;

    @Test
    void create() {
        Mockito.when(movementService.registrar(Mockito.any()))
                .thenReturn(Mono.just(new MovementResponseVO()));
        StepVerifier.create(movimientoController.registrar(MockUtils.buildMovementRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(movementService.listar())
                .thenReturn(Flux.just(MockUtils.buildMovementResponseVO()));
        StepVerifier.create(movimientoController.listar())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(movementService.actualizar(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movimientoController.actualizar(1L, MockUtils.buildMovementRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(movementService.eliminar(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movimientoController.eliminar(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void report() {
        Mockito.when(movementService.reporte(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Flux.just(MockUtils.buildStatementResponseVO()));
        StepVerifier.create(movimientoController.reporte(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}