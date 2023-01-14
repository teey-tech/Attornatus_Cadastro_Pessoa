package br.com.api.pessoa.exception.message;

public class DataViolationException extends RuntimeException {
  public DataViolationException(String message) {
    super(message);
  }
}
