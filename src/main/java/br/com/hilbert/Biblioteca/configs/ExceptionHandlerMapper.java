package br.com.hilbert.Biblioteca.configs;

import br.com.hilbert.Biblioteca.exceptions.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;

@RestControllerAdvice
public class ExceptionHandlerMapper {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus
                .NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Entidade não encontrada");
        problemDetail.setType(URI.create("https://api.biblioteca.com.br/problemas/entidade-nao-encontrada"));
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
}
