package br.com.treinaweb.ediarista.core.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.ediarista.core.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
