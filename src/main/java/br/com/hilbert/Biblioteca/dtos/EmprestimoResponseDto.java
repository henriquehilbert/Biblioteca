package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Cliente;
import br.com.hilbert.Biblioteca.models.Emprestimo;
import br.com.hilbert.Biblioteca.models.Exemplar;

import java.time.LocalDate;

public record EmprestimoResponseDto(Integer id,
                                    Exemplar exemplar,
                                    Cliente cliente,
                                    LocalDate data) {
    public static EmprestimoResponseDto toDto(Emprestimo emprestimo) {
        return new EmprestimoResponseDto(emprestimo.getId(), emprestimo.getExemplar(), emprestimo.getCliente(), emprestimo.getData());
    }
}
