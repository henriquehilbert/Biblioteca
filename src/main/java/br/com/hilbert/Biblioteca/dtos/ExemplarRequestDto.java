package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.models.Livro;

public record ExemplarRequestDto(Integer referencia,
                                 boolean disponivel,
                                 Integer idLivro) {

    public Exemplar toExemplar(Exemplar exemplar, Livro livro) {
        exemplar.setReferencia(this.referencia());
        exemplar.setDisponivel(this.disponivel());
        exemplar.setLivro(livro);

        return exemplar;
    }
}
