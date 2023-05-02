/**
 * 
 */

package com.nttdata.services;


import com.nttdata.common.exception.MovimientoException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author edwinleon
 *
 */
public interface ICRUD<T, ID> {

	Mono<T> registrar(T t) throws MovimientoException;

	Mono<T> modificar(T t) throws MovimientoException;

	Flux<T> listar() throws MovimientoException;

	Mono<T> listarPorId(ID id) throws MovimientoException;

	Mono<Void> eliminar(ID id) throws MovimientoException;
}
