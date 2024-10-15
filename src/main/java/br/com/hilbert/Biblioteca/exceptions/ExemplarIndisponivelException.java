package br.com.hilbert.Biblioteca.exceptions;

public class ExemplarIndisponivelException extends RegraNegocioException {

  public ExemplarIndisponivelException(String mensagem, Integer numeroRegraNegocio) {
    super(mensagem, numeroRegraNegocio);
  }
}