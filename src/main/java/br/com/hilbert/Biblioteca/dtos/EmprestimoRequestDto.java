package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Cliente;
import br.com.hilbert.Biblioteca.models.Emprestimo;
import br.com.hilbert.Biblioteca.models.Exemplar;

import java.time.LocalDate;

public record EmprestimoRequestDto(Exemplar exemplar,
                                   Cliente cliente,
                                   LocalDate data) {

    public Emprestimo toEmprestimo(Emprestimo emprestimo) {
        emprestimo.setExemplar(this.exemplar());
        emprestimo.setCliente(this.cliente());
        emprestimo.setData(this.data());

        return emprestimo;
    }
}
