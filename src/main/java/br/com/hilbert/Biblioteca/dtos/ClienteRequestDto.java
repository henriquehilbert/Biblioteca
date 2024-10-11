package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Cliente;

public record ClienteRequestDto(String nome,
                                String cpf,
                                String telefone,
                                String email,
                                boolean apto) {

    public Cliente toCliente(Cliente cliente) {
        cliente.setNome(this.nome());
        cliente.setCpf(this.cpf());
        cliente.setTelefone(this.telefone());
        cliente.setEmail(this.email());
        cliente.setApto(this.apto());
        return cliente;
    }
}
