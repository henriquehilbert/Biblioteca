package br.com.hilbert.Biblioteca.exceptions;

public class RegraNegocioException extends RuntimeException {

  private Integer numeroRegraNegocio;

  public RegraNegocioException(String mensagem, Integer numeroRegraNegocio) {
    super(mensagem);
    this.numeroRegraNegocio = numeroRegraNegocio;
  }

  public Integer getNumeroRegraNegocio() {
    return numeroRegraNegocio;
  }

  public void setNumeroRegraNegocio(Integer numeroRegraNegocio) {
    this.numeroRegraNegocio = numeroRegraNegocio;
  }
}
