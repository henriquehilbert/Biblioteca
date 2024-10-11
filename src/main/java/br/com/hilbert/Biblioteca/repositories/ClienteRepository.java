package br.com.hilbert.Biblioteca.repositories;

import br.com.hilbert.Biblioteca.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
