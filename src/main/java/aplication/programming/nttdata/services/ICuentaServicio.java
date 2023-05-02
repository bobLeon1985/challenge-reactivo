package aplication.programming.nttdata.services;

import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface ICuentaServicio {

    Mono<AccountResponseVO> registrar(AccountRequestVO request);

    Flux<AccountResponseVO> listar();

    Mono<Void> actualizar(Long idAccount, AccountRequestVO request);

    Mono<Void> eliminar(Long idAccount);
}
