package br.com.hilbert.Biblioteca.dtos;

import br.com.hilbert.Biblioteca.models.Categoria;
import br.com.hilbert.Biblioteca.models.Livro;

public record LivroRequestDto(String nome,
                              String autor,
                              int anoPublicacao,
                              Integer isbn,
                              Categoria categoria) {

    public Livro toLivro(Livro livro) {
        livro.setNome(this.nome());
        livro.setAutor(this.autor());
        livro.setAnoPublicacao(this.anoPublicacao());
        livro.setIsbn(this.isbn());
        livro.setCategoria(this.categoria());

        return livro;
    }
}



