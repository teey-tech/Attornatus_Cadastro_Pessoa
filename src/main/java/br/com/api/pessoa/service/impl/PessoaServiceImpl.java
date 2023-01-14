package br.com.api.pessoa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

import br.com.api.pessoa.exception.message.ObjectNotFoundException;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.persistence.repository.PessoaRepository;
import br.com.api.pessoa.service.PessoaService;

@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PessoaServiceImpl.class);

  @Autowired
  private PessoaRepository repository;

  @Override
  public Pessoa salvar(Pessoa pessoa) {
    LOGGER.info("Criando um Registro de Pessoa");
    notNull(pessoa, "ERRO NA REQUISIÇÃO");
    return repository.saveAndFlush(pessoa);

  }

  @Override
  public List<Pessoa> findAll() {
    LOGGER.info("Buscando todos os Usuarios cadastrados");
    return repository.findAll();
  }

  @Override
  public Pessoa findById(Long id) {
    LOGGER.info("Buscando Pessoa por id");
    notNull(id, "Id invalido");
    Optional<Pessoa> pessoaAchada = repository.findById(id);

    if (pessoaAchada.isEmpty()) {
      throw new ObjectNotFoundException("Pessoa não Encontrada");
    }

    return pessoaAchada.get();
  }

  @Override
  public Pessoa atualizar(Long id, Pessoa pessoa) {
    LOGGER.info("Atualizando Pessoa no banco de dados");
    notNull(id, "iD INVALIDO");
    notNull(pessoa, "Erro ao atualizar Pessoa");
    this.findById(id);
    pessoa.setId(id);
    return repository.saveAndFlush(pessoa);
  }

  @Override
  public void delete(Long id) {
    LOGGER.info("Deletando uma Pessoa");
    notNull(id, "Id invalido");
    this.findById(id);
    repository.deleteById(id);

  }
}
