package br.com.hilbert.Biblioteca.exceptions;

public class ExemplarNaoDisponivelException extends RegraNegocioException {

  public ExemplarNaoDisponivelException(String mensagem, Integer numeroRegraNegocio) {
    super(mensagem, numeroRegraNegocio);
  }
}