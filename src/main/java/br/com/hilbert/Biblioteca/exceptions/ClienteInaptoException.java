package br.com.hilbert.Biblioteca.exceptions;

public class ClienteInaptoException extends RegraNegocioException {

  public ClienteInaptoException(String mensagem, Integer numeroRegraNegocio) {
    super(mensagem, numeroRegraNegocio);
  }
}
