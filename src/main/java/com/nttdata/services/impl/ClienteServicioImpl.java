package com.nttdata.services.impl;

import com.nttdata.common.exception.BankMessageError;
import com.nttdata.dto.ClienteDto;
import com.nttdata.model.Cliente;
import com.nttdata.repository.ClienteRepository;
import com.nttdata.services.IClienteServicio;
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
public class ClienteServicioImpl implements IClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper mapper;

    @Override
    @Transactional
    public Mono<ClienteDto> registrar(ClienteDto request) {
        Cliente cliente = mapper.map(request ,Cliente.class);
        return clienteRepository.save(cliente)
                .onErrorResume(error -> {
                    log.error("An error occurred while creating the client. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT006);
                })
                .doOnSuccess(success -> log.info("Client created successfully"))
                .map(responseClient -> mapper.map(responseClient , ClienteDto.class));
    }

    @Override
    public Flux<ClienteDto> buscar() {
        return clienteRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for customers. Detail = {}", error.getMessage());
                    return Mono.error(BankMessageError.NTT007);
                })
                .map(cliente -> mapper.map(cliente,ClienteDto.class));
    }

    @Override
    @Transactional
    public Mono<Void> actualizar(Long idClient, ClienteDto request) {
       return clienteRepository.findById(idClient)
               .map(cliente -> {
                   request.setIdCliente(idClient);
                   Cliente c = mapper.map(request , Cliente.class);
                   return clienteRepository.save(c);
               })
               .onErrorResume(error -> {
                   log.error("An error occurred while updating the client. Detail = {}", error.getMessage());
                   return Mono.error(BankMessageError.NTT008);
               })
               .doOnSuccess(success -> log.info("Client successfully upgraded"))
               .then();
    }

    @Override
    @Transactional
    public Mono<Void> eliminar(Long idClient) {
       return clienteRepository.deleteById(idClient)
               .onErrorResume(error -> {
                   log.error("An error occurred while deleting the client. Detail = {}", error.getMessage());
                   return Mono.error(BankMessageError.NTT009);
               })
               .doOnSuccess(success -> log.info("Client successfully removed"));
    }
}
