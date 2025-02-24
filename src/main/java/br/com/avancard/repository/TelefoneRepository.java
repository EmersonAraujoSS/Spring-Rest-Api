package br.com.avancard.repository;

import br.com.avancard.model.Telefone;
import org.springframework.data.repository.CrudRepository;

public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
}
