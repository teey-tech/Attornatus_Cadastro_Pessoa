package br.com.api.pessoa.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.api.pessoa.exception.message.ObjectNotFoundException;
import br.com.api.pessoa.persistence.entity.Endereco;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.persistence.repository.EnderecoRepository;
import br.com.api.pessoa.persistence.repository.PessoaRepository;

@SpringBootTest
public class EnderecoServiceImplTest {

  @InjectMocks
  private EnderecoServiceImpl service;

  @Mock
  private EnderecoRepository repository;

  @Mock
  private PessoaRepository pessoaRepository;

  private Endereco endereco;

  private Optional<Endereco> optionalEndereco;

  private Pessoa pessoa;
  private Optional<Pessoa> optionalPessoa;

  /* Pessoa */
  private static final Long ID = (long) 1;
  private static final String NOME = "Thiago";
  private static final LocalDate DATA_NASCIMENTO = LocalDate.of(1998, 01, 27);

  private static final Integer INDEX = 0;

  /* Endereço */
  private static final String LOGRADOURO = "Travessa Alagoinha";
  private static final String CEP = "09071488";
  private static final Integer NUMERO = 69;
  private static final String CIDADE = "Santo André";
  private static final boolean ENDERECO_PRINCIPAL = true;

  private static final String ENDERECO_NÃO_ENCONTRADO = "Endereço não Encontrado";
  private static final String PESSOA_NÃO_ENCONTRADA = "Pessoa não Encontrada";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    startUp();
  }

  @Test
  void quandoSalvarRetornaSucesso(){
    when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
    when(repository.saveAndFlush(any())).thenReturn(endereco);
    Endereco response = service.salvar(endereco);

    assertNotNull(response);

    assertEquals(Endereco.class, response.getClass());

    assertEquals(ID, response.getId());
    assertEquals(LOGRADOURO, response.getLogradouro());
    assertEquals(CEP, response.getCep());
    assertEquals(NUMERO, response.getNumero());
    assertEquals(CIDADE, response.getCidade());
    assertEquals(ENDERECO_PRINCIPAL, response.getEnderecoPrincipal());

    assertEquals(ID, response.getPessoa().getId());
    assertEquals(NOME, response.getPessoa().getNome());
    assertEquals(DATA_NASCIMENTO, response.getPessoa().getDataNascimento());

  }

  @Test
    void quandoSalvarRetornaUmaException(){
    when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
    when(repository.saveAndFlush(any())).thenReturn(endereco);
    try {
    optionalPessoa.get().setId((long) 2);
    service.salvar(endereco);
    } catch (Exception ex) {
    assertEquals(ObjectNotFoundException.class, ex.getClass());
    assertEquals(PESSOA_NÃO_ENCONTRADA, ex.getMessage());
    }
  }

  @Test
  void quandoExecutarFindByIdRetornaUmaInstanciaDeEndereco(){
    when(repository.findById(anyLong())).thenReturn(optionalEndereco);
    Endereco response = service.findById(ID);

    assertNotNull(response);

    assertEquals(Endereco.class, response.getClass());

    assertEquals(ID, response.getId());
    assertEquals(LOGRADOURO, response.getLogradouro());
    assertEquals(CEP, response.getCep());
    assertEquals(NUMERO, response.getNumero());
    assertEquals(CIDADE, response.getCidade());
    assertEquals(ENDERECO_PRINCIPAL, response.getEnderecoPrincipal());

    assertEquals(ID, response.getPessoa().getId());
    assertEquals(NOME, response.getPessoa().getNome());
    assertEquals(DATA_NASCIMENTO, response.getPessoa().getDataNascimento());
  }

  @Test
    void quandoExecutarFindByIdERetornaUmaException(){
    when(repository.findById(anyLong()))
      .thenThrow(new ObjectNotFoundException(PESSOA_NÃO_ENCONTRADA));
    try {
      service.findById(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(PESSOA_NÃO_ENCONTRADA, ex.getMessage());
    }
  }

  @Test
  void quandoExecutarFindAllRetornaUmaListaDeEndereco(){
  when(repository.findAll()).thenReturn(List.of(endereco));
    List<Endereco> response = service.findAll();

    assertNotNull(response);

    assertEquals(Endereco.class, response.get(INDEX).getClass());
    assertEquals(1, response.size());

    assertEquals(ID, response.get(INDEX).getId());
    assertEquals(LOGRADOURO, response.get(INDEX).getLogradouro());
    assertEquals(CEP, response.get(INDEX).getCep());
    assertEquals(NUMERO, response.get(INDEX).getNumero());
    assertEquals(CIDADE, response.get(INDEX).getCidade());
    assertEquals(ENDERECO_PRINCIPAL, response.get(INDEX).getEnderecoPrincipal());

    assertEquals(ID, response.get(INDEX).getPessoa().getId());
    assertEquals(NOME, response.get(INDEX).getPessoa().getNome());
    assertEquals(DATA_NASCIMENTO,
    response.get(INDEX).getPessoa().getDataNascimento());

  }

  @Test
  void quandoExecutarFindAllEnderecoPrincipalRetornaUmaListaDeEndereco(){
  when(repository.findAllByEnderecoPrincipal(anyBoolean())).thenReturn(List.of(endereco));
    List<Endereco> response = service.findAllEnderecoPrincipal(ENDERECO_PRINCIPAL);

    assertNotNull(response);

    assertEquals(Endereco.class, response.get(INDEX).getClass());
    assertEquals(1, response.size());

    assertEquals(ID, response.get(INDEX).getId());
    assertEquals(LOGRADOURO, response.get(INDEX).getLogradouro());
    assertEquals(CEP, response.get(INDEX).getCep());
    assertEquals(NUMERO, response.get(INDEX).getNumero());
    assertEquals(CIDADE, response.get(INDEX).getCidade());
    assertEquals(ENDERECO_PRINCIPAL, response.get(INDEX).getEnderecoPrincipal());

    assertEquals(ID, response.get(INDEX).getPessoa().getId());
    assertEquals(NOME, response.get(INDEX).getPessoa().getNome());
    assertEquals(DATA_NASCIMENTO,
    response.get(INDEX).getPessoa().getDataNascimento());

  }

  @Test
    void quandoAtualizaComSucesso(){
    when(repository.saveAndFlush(any())).thenReturn(endereco);
    when(repository.findById(anyLong())).thenReturn(optionalEndereco);

    Endereco response = service.atualizar(ID, endereco);

    assertNotNull(response);

    assertEquals(Endereco.class, response.getClass());

    assertEquals(ID, response.getId());
    assertEquals(LOGRADOURO, response.getLogradouro());
    assertEquals(CEP, response.getCep());
    assertEquals(NUMERO, response.getNumero());
    assertEquals(CIDADE, response.getCidade());
    assertEquals(ENDERECO_PRINCIPAL, response.getEnderecoPrincipal());

    assertEquals(ID, response.getPessoa().getId());
    assertEquals(NOME, response.getPessoa().getNome());
    assertEquals(DATA_NASCIMENTO, response.getPessoa().getDataNascimento());
  }

  @Test
  void quandoAtualizarERetornaUmaObjectNotFoundException(){
    when(repository.saveAndFlush(any())).thenReturn(endereco);
    try {
      pessoa.setId((long) 2);
      service.atualizar(ID, endereco);
    }catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass() );
      assertEquals(ENDERECO_NÃO_ENCONTRADO, ex.getMessage());
    }
  }

  @Test
  void quandoDeletaComSucesso(){
    when(repository.findById(anyLong())).thenReturn(optionalEndereco);
    doNothing().when(repository).deleteById(ID);
    service.delete(ID);
    verify(repository, times(1)).deleteById(ID);
  }

  @Test
  void quandoDeletarERetornaUmaException(){
    when(repository.findById(anyLong()))
    .thenThrow(new ObjectNotFoundException(PESSOA_NÃO_ENCONTRADA));
    try {
      service.delete(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(PESSOA_NÃO_ENCONTRADA, ex.getMessage());
    }
  }

  void startUp() {
    pessoa = new Pessoa(ID, NOME, DATA_NASCIMENTO, null);
    optionalPessoa = Optional.of(new Pessoa(ID, NOME, DATA_NASCIMENTO, null));

    endereco = new Endereco(ID, LOGRADOURO, CEP, NUMERO, CIDADE,
        ENDERECO_PRINCIPAL, pessoa);
    optionalEndereco = Optional.of(new Endereco(ID, LOGRADOURO, CEP, NUMERO,
        CIDADE, ENDERECO_PRINCIPAL, pessoa));
  }
}
