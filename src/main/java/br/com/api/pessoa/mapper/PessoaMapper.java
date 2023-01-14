package br.com.api.pessoa.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.pessoa.model.request.PessoaRequest;
import br.com.api.pessoa.model.response.PessoaResponse;
import br.com.api.pessoa.persistence.entity.Pessoa;

@Component
public class PessoaMapper {

  @Autowired
  private ModelMapper mapper;

  public Pessoa toPessoa(PessoaRequest request) {
    return mapper.map(request, Pessoa.class);
  }

  public PessoaResponse toPessoaResponse(Pessoa request) {
    return mapper.map(request, PessoaResponse.class);
  }

  public List<PessoaResponse> toPessoaResponseList(List<Pessoa> pessoas) {
    return pessoas.stream()
        .map(this::toPessoaResponse)
        .collect(Collectors.toList());
  }
}
