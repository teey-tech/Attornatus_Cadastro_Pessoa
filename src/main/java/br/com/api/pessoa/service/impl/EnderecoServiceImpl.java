package br.com.api.pessoa.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

import br.com.api.pessoa.exception.message.DataViolationException;
import br.com.api.pessoa.exception.message.ObjectNotFoundException;
import br.com.api.pessoa.persistence.entity.Endereco;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.persistence.repository.EnderecoRepository;
import br.com.api.pessoa.persistence.repository.PessoaRepository;
import br.com.api.pessoa.service.EnderecoService;

@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoServiceImpl.class);

  @Autowired
  private EnderecoRepository repository;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Override
  public Endereco salvar(Endereco endereco) {
    LOGGER.info("Criando um Registro de Endereço");
    notNull(endereco, "ERRO NA REQUISIÇÃO");

    Optional<Pessoa> pessoaAchada = pessoaRepository.findById(endereco.getPessoa().getId());

    if (pessoaAchada.isEmpty()) {
      throw new ObjectNotFoundException("Pessoa não Encontrada");
    }

    this.verificarEnderecoPrincipal(endereco);
    endereco.setId(endereco.getId());
    endereco.setPessoa(pessoaAchada.get());
    return repository.saveAndFlush(endereco);

  }

  @Override
  public List<Endereco> findAll() {
    LOGGER.info("Buscando todos os Endereços cadastrados");
    return repository.findAll();
  }

  @Override
  public Endereco findById(Long id) {
    LOGGER.info("Buscando Endereço por id");
    notNull(id, "Id invalido");
    Optional<Endereco> enderecoAchado = repository.findById(id);

    return enderecoAchado.orElseThrow(() -> new ObjectNotFoundException("Endereço não Encontrado"));
  }

  @Override
  public Endereco atualizar(Long id, Endereco endereco) {
    LOGGER.info("Atualizando Registro no banco de dados");
    notNull(id, "iD INVALIDO");
    notNull(endereco, "Erro ao atualizar produto");
    this.findById(id);
    this.verificarEnderecoPrincipal(endereco);
    endereco.setId(id);
    return repository.saveAndFlush(endereco);
  }

  @Override
  public void delete(Long id) {
    LOGGER.info("Deletando um Produto");
    notNull(id, "Id invalido");
    this.findById(id);
    repository.deleteById(id);
  }

  @Override
  public List<Endereco> findAllEnderecoPrincipal(Boolean enderecoPrincipal) {
    LOGGER.info("Encontrando Todos endereços principais");
    notNull(enderecoPrincipal, "Não encontrado.");
    return repository.findAllByEnderecoPrincipal(enderecoPrincipal);
  }

  @Override
  public void verificarEnderecoPrincipal(Endereco endereco) {
    LOGGER.info("Verifica se o Usuario está tentando cadastrar 2 endereços principais");
    notNull(endereco, "ERRO");

    Optional<Endereco> enderecoAchado = repository
        .findByEnderecoPrincipalAndEnderecoPrincipalTrue(endereco.getEnderecoPrincipal());

    Optional<Pessoa> pessoaAchada = pessoaRepository
        .findById(endereco.getPessoa().getId());

    if (enderecoAchado.isPresent() && pessoaAchada.get().getId().equals(enderecoAchado.get().getId())) {
      throw new DataViolationException("Endereço Principal já cadastrado");
    }
  }
}
