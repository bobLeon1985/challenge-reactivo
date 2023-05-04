package com.nttdata.services.impl;

import com.nttdata.common.exception.BankMessageError;
import com.nttdata.dto.CuentaDto;
import com.nttdata.mapper.Mapper;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.services.ICuentaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CuentaServicioImpl implements ICuentaServicio {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Mono<CuentaDto> registrar(CuentaDto request) {
        log.info("Create account ");
        return clienteRepository.findById(request.getIdCliente())
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for the client. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT002);
                })
                .doOnSuccess(success -> log.info("Client successfully obtained"))
                .flatMap(client ->
                        cuentaRepository.save(Mapper.INSTANCE.cuentaDTotoCuenta(request, client.getIdCliente()))
                                .onErrorResume(error -> {
                                    log.error("An error occurred while trying to save the customer account. Detail = {}", error.getMessage());
                                    return Mono.error(BankMessageError.NTT002);
                                })
                                .doOnSuccess(success -> log.info("Customer account successfully saved"))
                                .map(accountResponse ->
                                        Mapper.INSTANCE.cuentaToCuentaDto(accountResponse, client.getNombre())
                                )

                )
                .doOnSuccess(success -> log.info("Client account creation process completed successfully"));
    }

    @Override
    public Flux<CuentaDto> buscar() {
        log.info("List all accounts");
        return cuentaRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while consulting the accounts. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT003);
                })
                .flatMap(cuenta -> clienteRepository.findById(cuenta.getIdCliente())
                        .map(client ->
                                Mapper.INSTANCE.cuentaToCuentaDto(cuenta, client.getNombre())
                        ));
    }

    @Override
    public Mono<CuentaDto> buxcarXId(Long id) {
        return cuentaRepository.findById(id)
                .onErrorResume(error -> {
                    log.error("An error occurred while consulting the accounts. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT003);
                })
                .flatMap(cuenta -> clienteRepository.findById(cuenta.getIdCliente())
                        .map(client ->
                                Mapper.INSTANCE.cuentaToCuentaDto(cuenta, client.getNombre())
                        ));
    }

    @Override
    @Transactional
    public Mono<Void> actualizar(CuentaDto request) {
        log.info("Start account update process");
        return cuentaRepository.findById(request.getIdCuenta())
                .flatMap(cuentaResponse ->
                        cuentaRepository.save(Mapper.INSTANCE.cuentaDTotoCuenta(request, cuentaResponse.getIdCliente()))
                                .onErrorResume(error -> {
                                    log.error("An error occurred while updating the customer account. Detail = {}", error.getMessage());
                                    return Mono.error(BankMessageError.NTT004);
                                })
                                .doOnSuccess(success -> log.info("Account update successful"))
                                .flatMap(response -> Mono.empty())
                );
    }

    @Override
    @Transactional
    public Mono<Void> eliminar(Long idCuenta) {
        log.info("Start account deletion process");
        return cuentaRepository.deleteById(idCuenta)
                .onErrorResume(error -> {
                    log.error("An error occurred while deleting the customer account. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT005);
                })
                .doOnSuccess(success -> log.info("Account deletion successful"));
    }
}
