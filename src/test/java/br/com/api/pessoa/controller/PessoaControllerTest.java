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

import br.com.api.pessoa.mapper.PessoaMapper;
import br.com.api.pessoa.model.request.PessoaRequest;
import br.com.api.pessoa.model.response.PessoaResponse;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.service.impl.PessoaServiceImpl;

@SpringBootTest
public class PessoaControllerTest {

  @InjectMocks
  private PessoaController controller;

  @Mock
  private PessoaServiceImpl service;

  @Mock
  private PessoaMapper mapper;

  private Pessoa pessoa;
  private PessoaRequest pessoaRequest;
  private PessoaResponse pessoaResponse;

  private static final Long ID = (long) 1;
  private static final String NOME = "Thiago";
  private static final LocalDate DATA_NASCIMENTO = LocalDate.of(1998, 01, 27);

  private static final Integer INDEX = 0;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    startUp();
  }

@Test
void quandoSalvarRetornaSucesso() {
when(service.salvar(any())).thenReturn(pessoa);
when(mapper.toPessoa(pessoaRequest)).thenReturn(pessoa);
when(mapper.toPessoaResponse(pessoa)).thenReturn(pessoaResponse);

ResponseEntity<PessoaResponse> response = controller.salvar(pessoaRequest);
assertEquals(HttpStatus.CREATED, response.getStatusCode());
assertEquals(ResponseEntity.class, response.getClass());

}

@Test
void quandoExecutarFindByIdERetornaSucesso(){
when(service.findById(anyLong())).thenReturn(pessoa);
when(mapper.toPessoaResponse(pessoa)).thenReturn(pessoaResponse);

ResponseEntity<PessoaResponse> response = controller.findById(ID);

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(ResponseEntity.class, response.getClass());
assertEquals(PessoaResponse.class, response.getBody().getClass() );

assertEquals(ID, response.getBody().getId());
assertEquals(NOME, response.getBody().getNome());
assertEquals(DATA_NASCIMENTO, response.getBody().getDataNascimento());
}

@Test
void quandoExecutarFindAllRetornaUmaListaDePessoas(){
when(service.findAll()).thenReturn(List.of(pessoa));
when(mapper.toPessoaResponseList(List.of(pessoa))).thenReturn(List.of(pessoaResponse));

ResponseEntity<List<PessoaResponse>> response = controller.findAll();

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(HttpStatus.OK, response.getStatusCode());
assertEquals(ResponseEntity.class, response.getClass());

assertEquals(ID, response.getBody().get(INDEX).getId());
assertEquals(NOME, response.getBody().get(INDEX).getNome());
assertEquals(DATA_NASCIMENTO,
response.getBody().get(INDEX).getDataNascimento());
}

@Test
void quandoAtualizarRetornaSucesso(){
when(service.atualizar(ID, pessoa)).thenReturn(pessoa);
when(mapper.toPessoa(pessoaRequest)).thenReturn(pessoa);
when(mapper.toPessoaResponse(pessoa)).thenReturn(pessoaResponse);
ResponseEntity<PessoaResponse> response = controller.atualizar(ID,
pessoaRequest);

assertNotNull(response);
assertNotNull(response.getBody());

assertEquals(HttpStatus.OK, response.getStatusCode());
assertEquals(ResponseEntity.class, response.getClass());
assertEquals(PessoaResponse.class, response.getBody().getClass());

assertEquals(ID, response.getBody().getId());
assertEquals(NOME, response.getBody().getNome());
assertEquals(DATA_NASCIMENTO, response.getBody().getDataNascimento());
}

  @Test
  void quandoDeletarRetornaSucesso() {
    doNothing().when(service).delete(anyLong());
    ResponseEntity<Void> response = controller.delete(ID);

    assertNotNull(response);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());

    verify(service, times(1)).delete(anyLong());

  }

  void startUp() {
    pessoa = new Pessoa(ID, NOME, DATA_NASCIMENTO, null);
    pessoaRequest = new PessoaRequest(NOME, DATA_NASCIMENTO);
    pessoaResponse = new PessoaResponse(ID, NOME, DATA_NASCIMENTO);
  }
}
