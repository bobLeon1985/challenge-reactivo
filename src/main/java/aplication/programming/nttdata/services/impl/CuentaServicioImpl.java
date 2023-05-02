package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.common.exception.BankError;
import aplication.programming.nttdata.enums.TipoCuentaEnum;
import aplication.programming.nttdata.model.Cuenta;
import aplication.programming.nttdata.repository.CuentaRepository;
import aplication.programming.nttdata.repository.ClienteRepository;
import aplication.programming.nttdata.services.ICuentaServicio;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
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
    public Mono<AccountResponseVO> registrar(AccountRequestVO request) {
        log.info("Create account ");
        return clienteRepository.findById(request.getId())
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for the client. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT001);
                })
                .doOnSuccess(success -> log.info("Client successfully obtained"))
                .flatMap(client -> {
                            Cuenta account = new Cuenta();
                            account.setNumeroCuenta(request.getAccountNumber());
                            account.setTipoCuenta(TipoCuentaEnum.valueOf(request.getAccountType()));
                            account.setSaldoInicial(request.getInitialBalance());
                            account.setEstado(request.getStatus());
                            account.setIdCliente(client.getIdCliente());

                            return cuentaRepository.save(account)
                                    .onErrorResume(error -> {
                                        log.error("An error occurred while trying to save the customer account. Detail = {}", error.getMessage());
                                        return Mono.error(BankError.NTT002);
                                    })
                                    .doOnSuccess(success -> log.info("Customer account successfully saved"))
                                    .map(accountResponse -> {
                                        AccountResponseVO accountResponseVO = new AccountResponseVO();
                                        accountResponseVO.setAccountNumber(accountResponse.getNumeroCuenta());
                                        accountResponseVO.setAccountType(accountResponse.getTipoCuenta().getDescripcion());
                                        accountResponseVO.setInitialBalance(accountResponse.getSaldoInicial());
                                        accountResponseVO.setStatus(accountResponse.getEstado());
                                        accountResponseVO.setName(client.getNombre());
                                        return accountResponseVO;
                                    });
                        }
                )
                .doOnSuccess(success -> log.info("Client account creation process completed successfully"));
    }

    @Override
    public Flux<AccountResponseVO> listar() {
        log.info("List all accounts");
        return cuentaRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while consulting the accounts. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT003);
                })
                .flatMap(account -> clienteRepository.findById(account.getIdCliente())
                        .map(client -> {
                            AccountResponseVO accountResponseVO = new AccountResponseVO();
                            accountResponseVO.setAccountNumber(account.getNumeroCuenta());
                            accountResponseVO.setAccountType(account.getTipoCuenta().getDescripcion());
                            accountResponseVO.setInitialBalance(account.getSaldoInicial());
                            accountResponseVO.setStatus(account.getEstado());
                            accountResponseVO.setName(client.getNombre());
                            return accountResponseVO;
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> actualizar(Long idAccount, AccountRequestVO request) {
            log.info("Start account update process");
        return Mono.just(request)
                .flatMap(accountRequestVO -> {
                    Cuenta account = new Cuenta();
                    account.setIdCuenta(accountRequestVO.getId());
                    account.setNumeroCuenta(accountRequestVO.getAccountNumber());
                    account.setTipoCuenta(TipoCuentaEnum.valueOf(accountRequestVO.getAccountType()));
                    account.setSaldoInicial(accountRequestVO.getInitialBalance());
                    account.setEstado(accountRequestVO.getStatus());
                    return cuentaRepository.save(account)
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
    public Mono<Void> eliminar(Long idAccount) {
        log.info("Start account deletion process");
        return cuentaRepository.deleteById(idAccount)
                .onErrorResume(error -> {
                    log.error("An error occurred while deleting the customer account. Detail = {}", error.getMessage());
                    return Mono.error(BankError.NTT005);
                })
                .doOnSuccess(success -> log.info("Account deletion successful"));
    }
}
