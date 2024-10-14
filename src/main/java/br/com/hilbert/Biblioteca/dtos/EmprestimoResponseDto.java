package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Emprestimo;

import java.time.LocalDate;

public record EmprestimoResponseDto(Integer id,
                                    Integer exemplarId,
                                    Integer clienteId,
                                    LocalDate data) {

    public static EmprestimoResponseDto toDto(Emprestimo emprestimo) {
        return new EmprestimoResponseDto(
                emprestimo.getId(),
                emprestimo.getExemplar().getId(),
                emprestimo.getCliente().getId(),
                emprestimo.getData()
        );
    }
}
