package br.com.hilbert.Biblioteca.controllers;

import br.com.hilbert.Biblioteca.dtos.ExemplarRequestDto;
import br.com.hilbert.Biblioteca.dtos.ExemplarResponseDto;
import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.repositories.ExemplarRepository;
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
    public ExemplarController(ExemplarRepository exemplarRepository) {
        this.exemplarRepository = exemplarRepository;
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
        Exemplar exemplar= dto.toExemplar(new Exemplar());
        exemplarRepository.save(exemplar);
        return ResponseEntity
                .created(URI.create("/exemplares/" + exemplar.getId()))
                .body(ExemplarResponseDto.toDto(exemplar));
    }
    @PutMapping("{id}")
    public ResponseEntity<ExemplarResponseDto> update(@PathVariable("id") Integer id, @RequestBody ExemplarRequestDto dto) {
        Optional<Exemplar> exemplarOpt = exemplarRepository.findById(id);

        if (exemplarOpt.isPresent()) {
            Exemplar exemplarSalvo = dto.toExemplar(exemplarOpt.get());
            return ResponseEntity
                    .ok(ExemplarResponseDto.toDto(exemplarRepository.save(exemplarSalvo)));
        } else {
            throw new EntityNotFoundException("Exemplar não encontrado.");
        }
    }
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        exemplarRepository.deleteById(id);
    }
}
