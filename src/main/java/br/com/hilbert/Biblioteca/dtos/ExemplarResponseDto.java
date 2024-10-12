package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.models.Livro;

public record ExemplarResponseDto(Integer id,
                                  Livro livro,
                                  int referencia,
                                  boolean disponivel) {

    public static ExemplarResponseDto toDto(Exemplar exemplar) {
        return new ExemplarResponseDto(exemplar.getId(), exemplar.getLivro(), exemplar.getReferencia(), exemplar.isDisponivel());
    }
}
