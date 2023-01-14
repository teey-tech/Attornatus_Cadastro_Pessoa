package br.com.api.pessoa.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import br.com.api.pessoa.exception.message.DataViolationException;
import br.com.api.pessoa.exception.message.ObjectNotFoundException;

@SpringBootTest
public class ResourceExceptionHandlerTest {

  @InjectMocks
  private ResourceExceptionHandler exeptionHandler;

  private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
  private static final String ENDERECO_PRINCIPAL_JA_CADASTRADO = "Endereço Principal já cadastrado";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void quandoAconteceUmObjectNotFoundExceptionERetornaUmResponseEntity() {
    ResponseEntity<StandardError> response = exeptionHandler
        .objectNotFound(
            new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO),
            new MockHttpServletRequest());

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(StandardError.class, response.getBody().getClass());
    assertEquals(OBJETO_NAO_ENCONTRADO, response.getBody().getError());
    assertEquals(404, response.getBody().getStatus());

  }

  @Test
  void whenDataIntegratyViolationExceptionThenReturnAResponseEntity() {
    ResponseEntity<StandardError> response = exeptionHandler
        .dataViolation(
            new DataViolationException(ENDERECO_PRINCIPAL_JA_CADASTRADO),
            new MockHttpServletRequest());

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(StandardError.class, response.getBody().getClass());
    assertEquals(ENDERECO_PRINCIPAL_JA_CADASTRADO, response.getBody().getError());
    assertEquals(400, response.getBody().getStatus());

  }

}
