package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Exemplar;

public record ExemplarResponseDto(Integer id,
                                  Integer referencia,
                                  boolean disponivel,
                                  Integer idLivro) {

    public static ExemplarResponseDto toDto(Exemplar exemplar) {
        return new ExemplarResponseDto(
                exemplar.getId(),
                exemplar.getReferencia(),
                exemplar.isDisponivel(),
                exemplar.getLivro().getId()
        );
    }
}

