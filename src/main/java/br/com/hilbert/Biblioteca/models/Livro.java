package br.com.hilbert.Biblioteca.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100, unique = true)
    private String nome;

    @Column(name = "autor", nullable = false, length = 50)
    private String autor;

    @Column(name = "ano", nullable = false)
    private int anoPublicacao;

    @Column(name = "isbn", nullable = false, unique = true)
    private Integer isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
    private Collection<Exemplar> exemplares = new ArrayList<>();

    public Livro() {
    }

    public Livro(Integer id, String nome, String autor, int anoPublicacao, int isbn, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Collection<Exemplar> getExemplares() {
        return exemplares;
    }
}
