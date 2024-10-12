package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.models.Livro;

public record ExemplarRequestDto(Livro livro,
                                 int referencia,
                                 boolean disponivel) {

    public Exemplar toExemplar(Exemplar exemplar) {
        exemplar.setLivro(livro());
        exemplar.setReferencia(referencia());
        exemplar.setDisponivel(disponivel());

        return exemplar;
    }
}
