package br.com.hilbert.Biblioteca.models;

import jakarta.persistence.*;

@Entity
@Table(name = "exemplares")
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    @Column(name = "ref", nullable = false)
    private Integer referencia;

    @Column(name = "disponivel")
    private boolean disponivel;

    public Exemplar() {
    }

    public Exemplar(Integer id, Livro livro, int referencia, boolean disponivel) {
        this.id = id;
        this.livro = livro;
        this.referencia = referencia;
        this.disponivel = disponivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
