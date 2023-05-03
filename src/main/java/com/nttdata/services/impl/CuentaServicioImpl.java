package com.nttdata.services.impl;

import com.nttdata.common.exception.BankError;
import com.nttdata.dto.CuentaDto;
import com.nttdata.mapper.Mapper;
import com.nttdata.model.Cuenta;
import com.nttdata.repository.CuentaRepository;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.services.ICuentaServicio;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper mapper;

    @Override
    @Transactional
    public Mono<CuentaDto> registrar(CuentaDto request) {
        log.info("Create account ");
        return clienteRepository.findById(request.getIdCliente())
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for the client. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT001);
                })
                .doOnSuccess(success -> log.info("Client successfully obtained"))
                .flatMap(client ->
                        cuentaRepository.save(Mapper.INSTANCE.cuentaDTotoCuenta(request, client.getIdCliente()))
                                .onErrorResume(error -> {
                                    log.error("An error occurred while trying to save the customer account. Detail = {}", error.getMessage());
                                    return Mono.error(BankError.NTT002);
                                })
                                .doOnSuccess(success -> log.info("Customer account successfully saved"))
                                .map(accountResponse ->
                                        Mapper.INSTANCE.cuentaToCuentaDto(accountResponse, client.getNombre())
                                )

                )
                .doOnSuccess(success -> log.info("Client account creation process completed successfully"));
    }

    @Override
    public Flux<CuentaDto> listar() {
        log.info("List all accounts");
        return cuentaRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while consulting the accounts. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT003);
                })
                .flatMap(account -> clienteRepository.findById(account.getIdCliente())
                        .map(client -> {
                            CuentaDto cuentaDto = mapper.map(account, CuentaDto.class);
                            cuentaDto.setName(client.getNombre());
                            return cuentaDto;
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> actualizar(Long idAccount, CuentaDto request) {
        log.info("Start account update process");
        return cuentaRepository.findById(request.getIdCuenta())
                .flatMap(cuentaResponse -> {
                    Cuenta cuenta = mapper.map(request, Cuenta.class);
                    cuenta.setIdCliente(cuentaResponse.getIdCliente());
                    return cuentaRepository.save(cuenta)
                            .onErrorResume(error -> {
                                log.error("An error occurred while updating the customer account. Detail = {}", error.getMessage());
                                return Mono.error(BankError.NTT004);
                            })
                            .doOnSuccess(success -> log.info("Account update successful"))
                            .flatMap(response -> Mono.empty());
                });
    }

    @Override
    @Transactional
    public Mono<Void> eliminar(Long idCuenta) {
        log.info("Start account deletion process");
        return cuentaRepository.deleteById(idCuenta)
                .onErrorResume(error -> {
                    log.error("An error occurred while deleting the customer account. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT005);
                })
                .doOnSuccess(success -> log.info("Account deletion successful"));
    }
}
