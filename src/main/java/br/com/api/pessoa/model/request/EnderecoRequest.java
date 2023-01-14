package br.com.api.pessoa.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequest {

  @JsonIgnore
  private Long id;

  @NotBlank(message = "Logradouro é Obrigatório")
  private String logradouro;

  @NotBlank(message = "CEP é Obrigatório")
  @Size(min = 8, max = 8, message = "O atributo CEP deve conter 8 Números")
  private String cep;

  @NotNull(message = "Número é Obrigatório")
  private Integer numero;

  @NotBlank(message = "Cidade é Obrigatório")
  private String cidade;

  @NotNull
  private Boolean enderecoPrincipal;

  @NotNull
  private Long pessoaId;
}
