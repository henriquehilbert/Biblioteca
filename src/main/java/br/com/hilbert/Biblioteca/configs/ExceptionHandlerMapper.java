package br.com.hilbert.Biblioteca.configs;

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

        problemDetail.setProperty("dataHoraErro", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ProblemDetail> handle(RegraNegocioException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Operação não pode ser concluída.");
        problemDetail.setDetail(ex.getMessage());

        problemDetail.setProperty("dataHoraErro", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
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

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setTitle("Erro interno do servidor");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/erro-interno"));
        return problemDetail;
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ProblemDetail handleRegraNegocioException(RegraNegocioException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Violação de regra de negócio");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/regra-negocio"));
        return problemDetail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Entidade não encontrada");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/entidade-nao-encontrada"));
        return problemDetail;
    }
}

