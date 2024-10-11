package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Cliente;

public record ClienteResponseDto(Integer id,
                                 String nome,
                                 String cpf,
                                 String telefone,
                                 String email,
                                 boolean apto) {
    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ClienteResponseDto(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEmail(), cliente.isApto());
    }
}

