package br.com.api.pessoa.model.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

  private Long id;

  private String nome;

  private LocalDate dataNascimento;
}
