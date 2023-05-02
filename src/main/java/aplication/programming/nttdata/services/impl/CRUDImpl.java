/**
 * 
 */
package aplication.programming.nttdata.services.impl;




import aplication.programming.nttdata.common.exception.MovimientoException;
import aplication.programming.nttdata.repository.IGenericRepo;
import aplication.programming.nttdata.services.ICRUD;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author edwinleon
 *
 */
public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

	protected abstract IGenericRepo<T, ID> getRepo();
	
	@Override
	public Mono<T> registrar(T t) throws MovimientoException {
		return getRepo().save(t);
	}

	@Override
	public Mono<T> modificar(T t) throws MovimientoException {
		return getRepo().save(t);
	}

	@Override
	public Flux<T> listar() throws MovimientoException {
		return getRepo().findAll();
	}

	@Override
	public Mono<T> listarPorId(ID id) throws MovimientoException {
		return getRepo().findById(id);
	}

	@Override
	public Mono<Void> eliminar(ID id) throws MovimientoException {
		return getRepo().deleteById(id);
	}

}

