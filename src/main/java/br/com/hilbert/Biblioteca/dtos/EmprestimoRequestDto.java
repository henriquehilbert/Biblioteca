package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Cliente;
import br.com.hilbert.Biblioteca.models.Emprestimo;
import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.repositories.ClienteRepository;
import br.com.hilbert.Biblioteca.repositories.ExemplarRepository;

import java.time.LocalDate;

public record EmprestimoRequestDto(Integer exemplarId,
                                   Integer clienteId,
                                   LocalDate data) {

    public Emprestimo toEmprestimo(Emprestimo emprestimo, ExemplarRepository exemplarRepository, ClienteRepository clienteRepository) {
        Exemplar exemplar = exemplarRepository.getReferenceById(this.exemplarId());
        Cliente cliente = clienteRepository.getReferenceById(this.clienteId());
        emprestimo.setExemplar(exemplar);
        emprestimo.setCliente(cliente);
        emprestimo.setData(this.data);

        return emprestimo;
    }
}
