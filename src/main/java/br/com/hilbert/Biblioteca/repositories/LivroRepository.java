package br.com.hilbert.Biblioteca.repositories;

import br.com.hilbert.Biblioteca.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
