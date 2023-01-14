package br.com.api.pessoa.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.pessoa.persistence.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

  public List<Endereco> findAllByEnderecoPrincipal(Boolean enderecoPrincipal);

  public Optional<Endereco> findByEnderecoPrincipalAndEnderecoPrincipalTrue(Boolean enderecoPrincipal);
}
