package br.com.hilbert.Biblioteca.controllers;

import br.com.hilbert.Biblioteca.dtos.EmprestimoRequestDto;
import br.com.hilbert.Biblioteca.dtos.EmprestimoResponseDto;
import br.com.hilbert.Biblioteca.models.Cliente;
import br.com.hilbert.Biblioteca.models.Emprestimo;
import br.com.hilbert.Biblioteca.models.Exemplar;
import br.com.hilbert.Biblioteca.repositories.ClienteRepository;
import br.com.hilbert.Biblioteca.repositories.EmprestimoRepository;
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
@RequestMapping("emprestimos")
public class EmprestimoController {

    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;
    private final ClienteRepository clienteRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository, ExemplarRepository exemplarRepository, ClienteRepository clienteRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.exemplarRepository = exemplarRepository;
        this.clienteRepository = clienteRepository;
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

        Optional<Exemplar> exemplarOpt = exemplarRepository.findById(dto.exemplarId());
        if (exemplarOpt.isEmpty()) {
            throw new EntityNotFoundException("Exemplar não encontrado.");
        }
        Exemplar exemplar = exemplarOpt.get();

        if (!exemplar.isDisponivel()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.clienteId());
        if (clienteOpt.isEmpty()) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
        Cliente cliente = clienteOpt.get();

        if (!cliente.isApto()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Emprestimo emprestimo = dto.toEmprestimo(new Emprestimo(), exemplarRepository, clienteRepository);

        exemplar.setDisponivel(false);
        exemplarRepository.save(exemplar);

        // Salvar o empréstimo
        emprestimoRepository.save(emprestimo);

        return ResponseEntity
                .created(URI.create("/emprestimos/" + emprestimo.getId()))
                .body(EmprestimoResponseDto.toDto(emprestimo));
    }

    @PutMapping("{id}")
    public ResponseEntity<EmprestimoResponseDto> update(@PathVariable("id") Integer id, @RequestBody EmprestimoRequestDto dto) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

        if (emprestimoOpt.isEmpty()) {
            throw new EntityNotFoundException("Empréstimo não encontrado.");
        }

        Exemplar exemplar = exemplarRepository.findById(dto.exemplarId())
                .orElseThrow(() -> new EntityNotFoundException("Exemplar não encontrado."));
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));

        Emprestimo emprestimo = dto.toEmprestimo(emprestimoOpt.get(), exemplarRepository, clienteRepository);

        if (!cliente.isApto()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!exemplar.isDisponivel() && !emprestimo.getExemplar().getId().equals(dto.exemplarId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok(EmprestimoResponseDto.toDto(emprestimo));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

        if (emprestimoOpt.isEmpty()) {
            throw new EntityNotFoundException("Empréstimo não encontrado.");
        }

        Emprestimo emprestimo = emprestimoOpt.get();
        Exemplar exemplar = emprestimo.getExemplar();

        exemplar.setDisponivel(true);
        exemplarRepository.save(exemplar);

        emprestimoRepository.deleteById(id);
    }
}
