package com.nttdata.common.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.common.exception.FailureException;
import com.nttdata.common.exception.FailureExceptionResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@Order(-2)
@RequiredArgsConstructor
public class CustomGlobalExceptionHandler implements ErrorWebExceptionHandler, InitializingBean {//extends AbstractErrorWebExceptionHandler {
    /**
     * Create a new {@code AbstractErrorWebExceptionHandler}.
     *
     * @param errorAttributes    the error attributes
     * @param resources          the resources configuration properties
     * @param applicationContext the application context
     * @param mapper
     * @since 2.4.0
     */
    /*public CustomGlobalExceptionHandler(ErrorAttributes errorAttributes,
                                        WebProperties.Resources resources,
                                        ApplicationContext applicationContext,
                                        ServerCodecConfigurer serverCodecConfigurer, ObjectMapper mapper) {
        super();
        /*super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());*/
    //this.mapper = mapper;
    //}

    /*@Override
    protected RouterFunction<ServerResponse> getRoutingFunction(
            ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request,
                ErrorAttributeOptions.defaults());

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));
    }*/

    private Map<Class<FailureException>, FailureExceptionResolver> errorResolver;
    private FailureExceptionResolver unhandledResolver;

    private final ObjectMapper mapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing error resolver");
        errorResolver = new HashMap<Class<FailureException>, FailureExceptionResolver>();
        errorResolver.put(FailureException.class, new FailureExceptionResolver());
        //errorResolver.put(ServerWebInputException.class, new ServerWebInputExceptionResolver());
        // unhandledResolver = new UnexpectedErrorResolver();
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return Mono.fromCallable(() -> errorResolver
                        .getOrDefault(throwable.getClass(), unhandledResolver))
                .flatMap(fn -> exchange.getResponse()
                        .writeWith(Mono
                                .fromCallable(() -> mapper
                                        .writeValueAsBytes(fn.apply(throwable).apply(exchange)))
                                .map(bufferFactory::wrap)));
    }
}
