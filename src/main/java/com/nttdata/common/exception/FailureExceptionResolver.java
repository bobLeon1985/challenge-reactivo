package com.nttdata.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import java.util.function.Function;

@Slf4j
public class FailureExceptionResolver implements
        Function<Throwable, Function<ServerWebExchange, ErrorResponse>> {

    @Override
    public Function<ServerWebExchange, ErrorResponse> apply(Throwable th) {
        FailureException ex = (FailureException) th;
        log.error("Failed by: {}", ((FailureException) th).getFailure());
        return exchange -> {
            exchange.getResponse().setRawStatusCode(ex.getErrorCode());
            return ex.getFailure();
        };
    }
}