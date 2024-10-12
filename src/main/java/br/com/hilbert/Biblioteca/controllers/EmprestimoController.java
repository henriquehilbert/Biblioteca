package br.com.hilbert.Biblioteca.controllers;

import br.com.hilbert.Biblioteca.dtos.EmprestimoRequestDto;
import br.com.hilbert.Biblioteca.dtos.EmprestimoResponseDto;
import br.com.hilbert.Biblioteca.exceptions.RegraNegocioException;
import br.com.hilbert.Biblioteca.models.Emprestimo;
import br.com.hilbert.Biblioteca.repositories.EmprestimoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("emprestimos")
public class EmprestimoController {

    private final EmprestimoRepository emprestimoRepository;
    public EmprestimoController(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }
    @GetMapping
    public ResponseEntity<Page<EmprestimoResponseDto>> findAll(@RequestParam(name = "numeroPagina", required = false, defaultValue = "0") int numeroPagina,
                                                               @RequestParam(name = "quantidade", required = false, defaultValue = "5") int quantidade) {
        PageRequest pageRequest = PageRequest.of(numeroPagina, quantidade);
        return ResponseEntity.ok(emprestimoRepository.findAll(pageRequest)
                .map(EmprestimoResponseDto::toDto));
    }
    @GetMapping("{id}")
    public ResponseEntity<EmprestimoResponseDto> findById(@PathVariable("id") Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);

        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(EmprestimoResponseDto.toDto(emprestimo.get()));
        } else {
            throw new EntityNotFoundException("Empréstimo não encontrado.");
        }
    }
    @PostMapping
    public ResponseEntity<EmprestimoResponseDto> save(@RequestBody EmprestimoRequestDto dto) {
        Emprestimo emprestimo = dto.toEmprestimo(new Emprestimo());

        if (!emprestimo.getExemplar().isDisponivel()) {
            throw new RegraNegocioException("Não é possível realizar empréstimo de um exemplar indisponível.", 400);
        }
        emprestimoRepository.save(emprestimo);

        emprestimo.getExemplar().setDisponivel(false);

        return ResponseEntity
                .created(URI.create("/emprestimos/" + emprestimo.getId()))
                .body(EmprestimoResponseDto.toDto(emprestimo));
    }



















    @PutMapping("{id}")
    public ResponseEntity<EmprestimoResponseDto> update(@PathVariable("id") Integer id, @RequestBody EmprestimoRequestDto dto) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimoSalvo = dto.toEmprestimo(emprestimoOpt.get());
            return ResponseEntity
                    .ok(EmprestimoResponseDto.toDto(emprestimoRepository.save(emprestimoSalvo)));
        } else {
            throw new EntityNotFoundException("Empréstimo não encontrado.");
        }
    }
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        emprestimoRepository.deleteById(id);
    }
}
