package br.com.api.pessoa.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.pessoa.model.request.EnderecoRequest;
import br.com.api.pessoa.model.response.EnderecoResponse;
import br.com.api.pessoa.persistence.entity.Endereco;

@Component
public class EnderecoMapper {

  @Autowired
  private ModelMapper mapper;

  public Endereco toEndereco(EnderecoRequest request) {
    return mapper.map(request, Endereco.class);
  }

  public EnderecoResponse toEnderecoResponse(Endereco request) {
    return mapper.map(request, EnderecoResponse.class);
  }

  public List<EnderecoResponse> toEnderecoResponseList(List<Endereco> enderecos) {
    return enderecos.stream()
        .map(this::toEnderecoResponse)
        .collect(Collectors.toList());
  }
}
