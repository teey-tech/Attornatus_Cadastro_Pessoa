package br.com.api.pessoa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.api.pessoa.mapper.EnderecoMapper;
import br.com.api.pessoa.model.request.EnderecoRequest;
import br.com.api.pessoa.model.response.EnderecoResponse;
import br.com.api.pessoa.model.response.PessoaResponse;
import br.com.api.pessoa.persistence.entity.Endereco;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.service.impl.EnderecoServiceImpl;

@SpringBootTest
public class EnderecoControllerTest {

  @InjectMocks
  private EnderecoController controller;

  @Mock
  private EnderecoMapper mapper;

  @Mock
  private EnderecoServiceImpl service;

  /* Pessoa */
  private static final Long ID = (long) 1;
  private static final String NOME = "Thiago";
  private static final LocalDate DATA_NASCIMENTO = LocalDate.of(1998, 01, 27);

  /* Endereço */
  private static final String LOGRADOURO = "Travessa Alagoinha";
  private static final String CEP = "09071488";
  private static final Integer NUMERO = 69;
  private static final String CIDADE = "Santo André";
  private static final Boolean ENDERECO_PRINCIPAL = true;

  private static final Integer INDEX = 0;

  private Endereco endereco;

  private Pessoa pessoa;
  private PessoaResponse pessoaResponse;

  private EnderecoRequest enderecoRequest;
  private EnderecoResponse enderecoResponse;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    startUp();
  }

@Test
void quandoSalvarRetornaSucesso() {
when(service.salvar(any())).thenReturn(endereco);
when(mapper.toEndereco(enderecoRequest)).thenReturn(endereco);
when(mapper.toEnderecoResponse(endereco)).thenReturn(enderecoResponse);

ResponseEntity<EnderecoResponse> response =
controller.salvar(enderecoRequest);
assertEquals(HttpStatus.CREATED, response.getStatusCode());
assertEquals(ResponseEntity.class, response.getClass());

}

@Test
void quandoExecutarFindByIdERetornaSucesso(){
when(service.findById(anyLong())).thenReturn(endereco);
when(mapper.toEnderecoResponse(endereco)).thenReturn(enderecoResponse);

ResponseEntity<EnderecoResponse> response = controller.findById(ID);

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(ResponseEntity.class, response.getClass());
assertEquals(EnderecoResponse.class, response.getBody().getClass() );

assertEquals(ID, response.getBody().getId());
assertEquals(LOGRADOURO, response.getBody().getLogradouro());
assertEquals(CEP, response.getBody().getCep());
assertEquals(NUMERO, response.getBody().getNumero());
assertEquals(CIDADE, response.getBody().getCidade());
assertEquals(ENDERECO_PRINCIPAL, response.getBody().getEnderecoPrincipal());

}

@Test
void quandoExecutarFindAllRetornaUmaListaDeEndereco(){
when(service.findAll()).thenReturn(List.of(endereco));
when(mapper.toEnderecoResponseList(List.of(endereco))).thenReturn(List.of(enderecoResponse));

ResponseEntity<List<EnderecoResponse>> response = controller.findAll();

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(HttpStatus.OK, response.getStatusCode());
assertEquals(ResponseEntity.class, response.getClass());

assertEquals(ID, response.getBody().get(INDEX).getId());
assertEquals(LOGRADOURO, response.getBody().get(INDEX).getLogradouro());
assertEquals(CEP, response.getBody().get(INDEX).getCep());
assertEquals(NUMERO, response.getBody().get(INDEX).getNumero());
assertEquals(CIDADE, response.getBody().get(INDEX).getCidade());
assertEquals(ENDERECO_PRINCIPAL,
response.getBody().get(INDEX).getEnderecoPrincipal());
}

@Test
void quandoAtualizaUmEnderecoComSucesso(){
when(service.atualizar(ID, endereco)).thenReturn(endereco);
when(mapper.toEndereco(enderecoRequest)).thenReturn(endereco);
when(mapper.toEnderecoResponse(endereco)).thenReturn(enderecoResponse);
ResponseEntity<EnderecoResponse> response = controller.atualizar(ID,
enderecoRequest);

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(ID, response.getBody().getId());
assertEquals(LOGRADOURO, response.getBody().getLogradouro());
assertEquals(CEP, response.getBody().getCep());
assertEquals(NUMERO, response.getBody().getNumero());
assertEquals(CIDADE, response.getBody().getCidade());
assertEquals(ENDERECO_PRINCIPAL, response.getBody().getEnderecoPrincipal());
}

  @Test
  void quandoDeletaComSucesso() {
    doNothing().when(service).delete(anyLong());
    ResponseEntity<Void> response = controller.delete(ID);

    assertNotNull(response);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());

    verify(service, times(1)).delete(anyLong());

  }

  void startUp() {
    pessoa = new Pessoa(ID, NOME, DATA_NASCIMENTO, null);
    pessoaResponse = new PessoaResponse(ID, NOME, DATA_NASCIMENTO);
    endereco = new Endereco(ID, LOGRADOURO, CEP, NUMERO, CIDADE,
        ENDERECO_PRINCIPAL, pessoa);
    enderecoRequest = new EnderecoRequest(ID, LOGRADOURO, CEP, NUMERO, CIDADE,
        ENDERECO_PRINCIPAL, ID);
    enderecoResponse = new EnderecoResponse(ID, LOGRADOURO, CEP, NUMERO, CIDADE,
        ENDERECO_PRINCIPAL, pessoaResponse);

  }
}
