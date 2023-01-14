package br.com.api.pessoa.service.impl;

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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.api.pessoa.exception.message.ObjectNotFoundException;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.persistence.repository.PessoaRepository;

@SpringBootTest
public class PessoaServiceImplTest {

  @InjectMocks
  private PessoaServiceImpl service;

  @Mock
  private PessoaRepository repository;

  private Pessoa pessoa;
  private Optional<Pessoa> optionalPessoa;

  /* Pessoa */
  private static final Long ID = (long) 1;
  private static final String NOME = "Thiago";
  private static final LocalDate DATA_NASCIMENTO = LocalDate.of(1998, 01, 27);

  private static final Integer INDEX = 0;

  private static final String PESSOA_NÃO_ENCONTRADA = "Pessoa não Encontrada";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    startUp();
  }

@Test
void quandoSalvarRetornaSucesso(){
when(repository.saveAndFlush(any())).thenReturn(pessoa);
Pessoa response = service.salvar(pessoa);

assertNotNull(response);

assertEquals(Pessoa.class, response.getClass());

assertEquals(ID, response.getId());
assertEquals(NOME, response.getNome());
assertEquals(DATA_NASCIMENTO, response.getDataNascimento());

}

@Test
void quandoExecutarFindByIdRetornaUmaInstanciaDePessoa(){
when(repository.findById(anyLong())).thenReturn(optionalPessoa);
Pessoa response = service.findById(ID);

assertNotNull(response);

assertEquals(Pessoa.class, response.getClass());

assertEquals(ID, response.getId());
assertEquals(NOME, response.getNome());
assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
}

@Test
void quandoExecutarFindByIdERetornaUmaException(){
when(repository.findById(anyLong())).thenThrow(new
ObjectNotFoundException(PESSOA_NÃO_ENCONTRADA));
try {
service.findById(ID);
} catch (Exception ex) {
assertEquals(ObjectNotFoundException.class, ex.getClass());
assertEquals(PESSOA_NÃO_ENCONTRADA, ex.getMessage());
}
}

@Test
void quandoExecutarFindAllRetornaUmaListaDePessoas(){
when(repository.findAll()).thenReturn(List.of(pessoa));
List<Pessoa> response = service.findAll();

assertNotNull(response);

assertEquals(Pessoa.class, response.get(INDEX).getClass());
assertEquals(1, response.size());

assertEquals(ID, response.get(INDEX).getId());
assertEquals(NOME, response.get(INDEX).getNome());
assertEquals(DATA_NASCIMENTO, response.get(INDEX).getDataNascimento());

}

@Test
void quandoAtualizaUmaPessoaComSucesso(){
when(repository.saveAndFlush(any())).thenReturn(pessoa);
when(repository.findById(anyLong())).thenReturn(optionalPessoa);

Pessoa response = service.atualizar(ID, pessoa);

assertNotNull(response);

assertEquals(Pessoa.class, response.getClass());

assertEquals(ID, response.getId());
assertEquals(NOME, response.getNome());
assertEquals(DATA_NASCIMENTO, response.getDataNascimento());
}

@Test
void quandoAtualizarERetornaUmaObjectNotFoundException(){
when(repository.saveAndFlush(any())).thenReturn(pessoa);
try {
pessoa.setId((long) 2);
service.atualizar(ID, pessoa);
} catch (Exception ex) {
assertEquals(ObjectNotFoundException.class, ex.getClass() );
assertEquals(PESSOA_NÃO_ENCONTRADA, ex.getMessage());
}
}

@Test
void quandoDeletaComSucesso(){
when(repository.findById(anyLong())).thenReturn(optionalPessoa);
doNothing().when(repository).deleteById(ID);
service.delete(ID);
verify(repository, times(1)).deleteById(ID);
}

@Test
void quandoDeletarRetornaUmaException(){
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
  }
}
