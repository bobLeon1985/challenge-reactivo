package aplication.programming.nttdata.services;

import aplication.programming.nttdata.dto.MovimientoDto;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;


@Validated
public interface IMovimientoServicio {

    Mono<MovementResponseVO> registrar(MovimientoDto request);

    Flux<MovementResponseVO> listar();

    Mono<Void> actualizar(Long idMovimiento, MovimientoDto request);

    Mono<Void>  eliminar(Long idMovimiento);

    Flux<StatementResponseVO> reporte(Date fechaInicio, Date fechaFin, String identificacion);

}
