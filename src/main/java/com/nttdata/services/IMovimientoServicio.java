package com.nttdata.services;

import com.nttdata.dto.MovimientoDto;
import com.nttdata.vo.response.MovementResponseVO;
import com.nttdata.vo.response.StatementResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;

@Validated
public interface IMovimientoServicio {

    Mono<MovimientoDto> registrarMovimiento(MovimientoDto request);

    Flux<MovimientoDto> buscar();

    Flux<MovimientoDto> buscarXCuenta(Long numerocuenta);


    Mono<Void> actualizar(Long idMovimiento, MovimientoDto request);

    Mono<Boolean>  eliminar(Long idMovimiento);



}
