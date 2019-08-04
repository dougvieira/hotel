package br.com.douglas.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.douglas.hotel.domain.Hospede;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long>{
	Hospede findByDocumento(String documento);
	Hospede findByNome(String nome);
	Hospede findByTelefone(String telefone);	
}