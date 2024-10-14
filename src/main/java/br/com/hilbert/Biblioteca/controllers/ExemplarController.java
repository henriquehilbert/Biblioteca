package br.com.hilbert.Biblioteca.controllers;

import br.com.hilbert.Biblioteca.dtos.ExemplarRequestDto;
import br.com.hilbert.Biblioteca.dtos.ExemplarResponseDto;
import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.models.Livro;
import br.com.hilbert.Biblioteca.repositories.ExemplarRepository;
import br.com.hilbert.Biblioteca.repositories.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("exemplares")
public class ExemplarController {

    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    public ExemplarController(ExemplarRepository exemplarRepository, LivroRepository livroRepository) {
        this.exemplarRepository = exemplarRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public ResponseEntity<Page<ExemplarResponseDto>> findAll(@RequestParam(name = "numeroPagina", required = false, defaultValue = "0") int numeroPagina,
                                                             @RequestParam(name = "quantidade", required = false, defaultValue = "5") int quantidade) {
        PageRequest pageRequest = PageRequest.of(numeroPagina, quantidade);
        return ResponseEntity.ok(exemplarRepository.findAll(pageRequest)
                .map(ExemplarResponseDto::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ExemplarResponseDto> findById(@PathVariable("id") Integer id) {
        Optional<Exemplar> exemplar = exemplarRepository.findById(id);

        if (exemplar.isPresent()) {
            return ResponseEntity.ok(ExemplarResponseDto.toDto(exemplar.get()));
        } else {
            throw new EntityNotFoundException("Exemplar não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<ExemplarResponseDto> save(@RequestBody ExemplarRequestDto dto) {
        // Buscar o livro pelo id informado no DTO
        Optional<Livro> livroOpt = livroRepository.findById(dto.idLivro());
        if (livroOpt.isEmpty()) {
            throw new EntityNotFoundException("Livro não encontrado.");
        }

        Exemplar exemplar = dto.toExemplar(new Exemplar(), livroOpt.get());
        exemplarRepository.save(exemplar);

        return ResponseEntity
                .created(URI.create("/exemplares/" + exemplar.getId()))
                .body(ExemplarResponseDto.toDto(exemplar));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExemplarResponseDto> update(@PathVariable("id") Integer id, @RequestBody ExemplarRequestDto dto) {

        Optional<Exemplar> exemplarOpt = exemplarRepository.findById(id);
        if (exemplarOpt.isEmpty()) {
            throw new EntityNotFoundException("Exemplar não encontrado.");
        }

        Optional<Livro> livroOpt = livroRepository.findById(dto.idLivro());
        if (livroOpt.isEmpty()) {
            throw new EntityNotFoundException("Livro não encontrado.");
        }

        Exemplar exemplarAtualizado = dto.toExemplar(exemplarOpt.get(), livroOpt.get());
        exemplarRepository.save(exemplarAtualizado);

        return ResponseEntity.ok(ExemplarResponseDto.toDto(exemplarAtualizado));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        exemplarRepository.deleteById(id);
    }
}
