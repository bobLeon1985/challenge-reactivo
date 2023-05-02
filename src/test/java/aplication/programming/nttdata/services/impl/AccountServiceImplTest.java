package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.utils.MockUtils;
import aplication.programming.nttdata.common.exception.BankError;
import aplication.programming.nttdata.repository.CuentaRepository;
import aplication.programming.nttdata.repository.ClienteRepository;
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
@ContextConfiguration(classes = {AccountServiceImplTest.class})
class AccountServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private CuentaServicioImpl accountService;

    @Test
    void create() {
        Mockito.when(clienteRepository.findByIdentificacion(Mockito.any()))
                        .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(cuentaRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        StepVerifier.create(accountService.registrar(MockUtils.buildAccountRequestVO()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

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
}