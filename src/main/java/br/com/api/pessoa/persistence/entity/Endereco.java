package br.com.api.pessoa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "logradouro", nullable = false)
  private String logradouro;

  @Column(name = "CEP", nullable = false, length = 8)
  private String cep;

  @Column(name = "numero", nullable = false)
  private Integer numero;

  @Column(name = "cidade", nullable = false)
  private String cidade;

  @Column(name = "endereco_principal", nullable = true)
  private Boolean enderecoPrincipal;

  @ManyToOne
  @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
  @JsonIgnoreProperties("endereco")
  private Pessoa pessoa;
}
