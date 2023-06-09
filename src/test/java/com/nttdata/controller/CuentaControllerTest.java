package com.nttdata.controller;

import com.nttdata.dto.CuentaDto;
import com.nttdata.utils.MockUtils;
import com.nttdata.services.ICuentaServicio;
import com.nttdata.vo.response.AccountResponseVO;
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
@ContextConfiguration(classes = {CuentaControllerTest.class})
class CuentaControllerTest {

    @Mock
    private ICuentaServicio accountService;

    @InjectMocks
    private CuentaController cuentaController;

    @Test
    void create() {
        Mockito.when(accountService.registrar(Mockito.any()))
                .thenReturn(Mono.just(new CuentaDto()));
        StepVerifier.create(cuentaController.registrar(MockUtils.buildCuentaDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(accountService.buscar())
                .thenReturn(Flux.just(MockUtils.buildCuentaDto()));
        StepVerifier.create(cuentaController.buscar())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(accountService.actualizar(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(cuentaController.actualizar( MockUtils.buildCuentaDto()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(accountService.eliminar(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(cuentaController.eliminar(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}