package br.com.api.pessoa.exception.message;

public class ObjectNotFoundException extends RuntimeException {
  public ObjectNotFoundException(String message) {
    super(message);
  }
}
