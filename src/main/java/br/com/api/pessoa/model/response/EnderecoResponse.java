package br.com.api.pessoa.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {

  private Long id;

  private String logradouro;

  private String cep;

  private Integer numero;

  private String cidade;

  private Boolean enderecoPrincipal;

  private PessoaResponse pessoa;
}
