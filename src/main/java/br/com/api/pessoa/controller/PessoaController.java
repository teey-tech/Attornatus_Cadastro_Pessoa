package br.com.api.pessoa.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.pessoa.mapper.PessoaMapper;
import br.com.api.pessoa.model.request.PessoaRequest;
import br.com.api.pessoa.model.response.PessoaResponse;
import br.com.api.pessoa.persistence.entity.Pessoa;
import br.com.api.pessoa.service.impl.PessoaServiceImpl;

@RestController
@RequestMapping(value = "v1/pessoa")
@CrossOrigin(allowedHeaders = "*", maxAge = 3500)
public class PessoaController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PessoaController.class);

  @Autowired
  private PessoaServiceImpl service;

  @Autowired
  private PessoaMapper mapper;

  @PostMapping
  public ResponseEntity<PessoaResponse> salvar(@Valid @RequestBody PessoaRequest request) {
    LOGGER.info("Requisição recebida {} ", request);
    Pessoa pessoa = mapper.toPessoa(request);
    Pessoa pessoaSalva = service.salvar(pessoa);
    PessoaResponse pessoaResponse = mapper.toPessoaResponse(pessoaSalva);

    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponse);
  }

  @GetMapping
  public ResponseEntity<List<PessoaResponse>> findAll() {
    LOGGER.info("Buscando todos os registros");
    List<Pessoa> listaDePessoa = service.findAll();
    List<PessoaResponse> pessoaResponseList = mapper.toPessoaResponseList(listaDePessoa);

    return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseList);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<PessoaResponse> findById(@PathVariable("id") Long id) {
    LOGGER.info("Buscando o registro {} ", id);
    Pessoa pessoaAchada = service.findById(id);
    PessoaResponse pessoaResponse = mapper.toPessoaResponse(pessoaAchada);

    return ResponseEntity.status(HttpStatus.OK).body(pessoaResponse);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<PessoaResponse> atualizar(@PathVariable("id") Long id,
      @Valid @RequestBody PessoaRequest request) {
    LOGGER.info("Atualizando o registro {} ", id);

    Pessoa pessoa = mapper.toPessoa(request);
    Pessoa pessoaAtualizada = service.atualizar(id, pessoa);
    PessoaResponse pessoaResponse = mapper.toPessoaResponse(pessoaAtualizada);

    return ResponseEntity.status(HttpStatus.OK).body(pessoaResponse);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    LOGGER.info("Deletando Registro {} ", id);
    service.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
