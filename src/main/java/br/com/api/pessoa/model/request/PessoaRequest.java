package br.com.api.pessoa.model.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {

  @NotBlank(message = "Nome é Obrigatório!")
  private String nome;

  @NotNull(message = "Data de Nascimento é Obrigatório!")
  private LocalDate dataNascimento;
}
