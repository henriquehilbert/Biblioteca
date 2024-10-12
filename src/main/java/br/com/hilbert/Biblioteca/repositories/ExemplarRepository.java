package br.com.hilbert.Biblioteca.repositories;

import br.com.hilbert.Biblioteca.models.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {
}
