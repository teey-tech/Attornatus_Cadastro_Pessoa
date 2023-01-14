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

import br.com.api.pessoa.mapper.EnderecoMapper;
import br.com.api.pessoa.model.request.EnderecoRequest;
import br.com.api.pessoa.model.response.EnderecoResponse;
import br.com.api.pessoa.persistence.entity.Endereco;
import br.com.api.pessoa.service.impl.EnderecoServiceImpl;

@RestController
@RequestMapping(value = "v1/endereco")
@CrossOrigin(allowedHeaders = "*", maxAge = 3500)
public class EnderecoController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoController.class);

  @Autowired
  private EnderecoServiceImpl service;

  @Autowired
  private EnderecoMapper mapper;

  @PostMapping
  public ResponseEntity<EnderecoResponse> salvar(@Valid @RequestBody EnderecoRequest request) {
    LOGGER.info("Requisição recebida {} ", request);
    Endereco endereco = mapper.toEndereco(request);
    Endereco enderecoSalvo = service.salvar(endereco);
    EnderecoResponse enderecoResponse = mapper.toEnderecoResponse(enderecoSalvo);

    return ResponseEntity.status(HttpStatus.CREATED).body(enderecoResponse);
  }

  @GetMapping
  public ResponseEntity<List<EnderecoResponse>> findAll() {
    LOGGER.info("Buscando todos os registros");
    List<Endereco> listaDeEndereco = service.findAll();
    List<EnderecoResponse> enderecoResponseList = mapper.toEnderecoResponseList(listaDeEndereco);

    return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseList);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<EnderecoResponse> findById(@PathVariable("id") Long id) {
    LOGGER.info("Buscando o registro {} ", id);
    Endereco enderecoAchado = service.findById(id);
    EnderecoResponse enderecoResponse = mapper.toEnderecoResponse(enderecoAchado);

    return ResponseEntity.status(HttpStatus.OK).body(enderecoResponse);
  }

  @GetMapping(value = "endereco-principal/{enderecoPrincipal}")
  public ResponseEntity<List<EnderecoResponse>> findByIdAndEnderecoPrincipal(
      @PathVariable("enderecoPrincipal") Boolean enderecoPrincipal) {
    List<Endereco> pessoaAchada = service.findAllEnderecoPrincipal(enderecoPrincipal);
    List<EnderecoResponse> enderecoResponseList = mapper.toEnderecoResponseList(pessoaAchada);

    return ResponseEntity.status(HttpStatus.OK).body(enderecoResponseList);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<EnderecoResponse> atualizar(@Valid @RequestBody @PathVariable("id") Long id,
      EnderecoRequest request) {
    LOGGER.info("Atualizando o registro {} ", id);

    Endereco endereco = mapper.toEndereco(request);
    Endereco enderecoAtualizado = service.atualizar(id, endereco);
    EnderecoResponse enderecoResponse = mapper.toEnderecoResponse(enderecoAtualizado);

    return ResponseEntity.status(HttpStatus.OK).body(enderecoResponse);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    LOGGER.info("Deletando Registro {} ", id);
    service.delete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
