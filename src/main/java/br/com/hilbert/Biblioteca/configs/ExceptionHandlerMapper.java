package br.com.hilbert.Biblioteca.configs;

import br.com.hilbert.Biblioteca.exceptions.ClienteInaptoException;
import br.com.hilbert.Biblioteca.exceptions.ExemplarIndisponivelException;
import br.com.hilbert.Biblioteca.exceptions.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ExceptionHandlerMapper {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handle(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Entidade não encontrada");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("dataHoraErro", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));

        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ProblemDetail> handle(RegraNegocioException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Operação não pôde ser concluída.");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("dataHoraErro", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
        problemDetail.setProperty("numeroRegraNegocio", ex.getNumeroRegraNegocio());

        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .CONFLICT, "O CPF ou ISBN já está cadastrado.");
        problemDetail.setTitle("Conflito de integridade de dados");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/conflito-dados"));
        return problemDetail;
    }

    @ExceptionHandler(ClienteInaptoException.class)
    public ProblemDetail handleClienteInaptoException(ClienteInaptoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .BAD_REQUEST, "Este cliente não está apto para realizar empréstimos.");
        problemDetail.setTitle("Cliente Inapto");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/cliente-inapto"));
        return problemDetail;
    }

    @ExceptionHandler(ExemplarIndisponivelException.class)
    public ProblemDetail handleExemplarIndisponivelException(ExemplarIndisponivelException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .BAD_REQUEST, "Este exemplar não está disponível para empréstimo.");
        problemDetail.setTitle("Exemplar Indisponível");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/exemplar-indisponivel"));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro interno do servidor");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/erro-interno"));
        return problemDetail;
    }
}

