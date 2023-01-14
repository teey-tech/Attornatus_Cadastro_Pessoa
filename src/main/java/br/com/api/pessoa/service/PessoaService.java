package br.com.api.pessoa.service;

import java.util.List;

import br.com.api.pessoa.persistence.entity.Pessoa;

public interface PessoaService {

  Pessoa salvar(Pessoa pessoa);

  List<Pessoa> findAll();

  Pessoa findById(Long id);

  Pessoa atualizar(Long id, Pessoa pessoa);

  void delete(Long id);

}
