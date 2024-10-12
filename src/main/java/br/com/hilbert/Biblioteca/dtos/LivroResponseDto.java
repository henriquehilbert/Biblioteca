package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Livro;
import br.com.hilbert.Biblioteca.models.Categoria;

public record LivroResponseDto(Integer id,
                               String nome,
                               String autor,
                               int anoPublicacao,
                               Integer isbn,
                               Categoria categoria) {

    public static LivroResponseDto toDto(Livro livro) {
        return new LivroResponseDto(livro.getId(), livro.getNome(), livro.getAutor(), livro.getAnoPublicacao(), livro.getIsbn(), livro.getCategoria());
    }
}
