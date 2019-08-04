package br.com.douglas.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglas.hotel.domain.Hospede;
import br.com.douglas.hotel.repository.HospedeRepository;

@Service
public class HospedeService {

	@Autowired
	private HospedeRepository repository;
	
	public Hospede save(Hospede hospede) {
		return repository.save(hospede);
	}
	
	public void delete(Hospede hospede) {
		repository.delete(hospede);
	}
	
	public List<Hospede> findAll(){
		return repository.findAll();			
	}	
	
	public Hospede findByDocument(String documento) {
		return repository.findByDocumento(documento);
	}
	
	public Hospede findByNome(String nome){
		return repository.findByNome(nome);
	}
	
	public Hospede findByTelefone(String telefone){
		return repository.findByTelefone(telefone);
	} 		
}
