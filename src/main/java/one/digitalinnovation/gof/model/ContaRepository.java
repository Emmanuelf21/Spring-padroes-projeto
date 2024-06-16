package one.digitalinnovation.gof.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ContaRepository
 */
@Repository
public interface ContaRepository extends CrudRepository<Conta, String>{

    
}