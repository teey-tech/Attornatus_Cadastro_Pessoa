package br.com.api.pessoa.service;

import java.util.List;

import br.com.api.pessoa.persistence.entity.Endereco;

public interface EnderecoService {

  Endereco salvar(Endereco endereco);

  List<Endereco> findAll();

  Endereco findById(Long id);

  Endereco atualizar(Long id, Endereco endereco);

  void delete(Long id);

  List<Endereco> findAllEnderecoPrincipal(Boolean enderecoPrincipal);

  void verificarEnderecoPrincipal(Endereco endereco);
}
