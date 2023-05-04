package com.nttdata.services;

import com.nttdata.vo.response.StatementResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.sql.Date;
@Validated
public interface IReporteServicio {
    Flux<StatementResponseVO> reporte(Date fechaInicio, Date fechaFin, String identificacion);
}
