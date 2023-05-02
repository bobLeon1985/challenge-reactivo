/**
 * 
 */
package aplication.programming.nttdata.repository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author edwinleon
 *
 */
@NoRepositoryBean
public interface IGenericRepo<T, ID> extends ReactiveCrudRepository<T, ID> {

}

