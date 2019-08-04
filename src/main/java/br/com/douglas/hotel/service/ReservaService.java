package br.com.douglas.hotel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.douglas.hotel.domain.Reserva;
import br.com.douglas.hotel.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository repository;
	
	public Reserva save(Reserva reserva) {
		return repository.save(reserva);
	}
	
	public void delete(Reserva reserva) {
		repository.delete(reserva);
	}
	
	public List<Reserva> findAll(){
		return repository.findAll();			
	}	
	
	public List<Reserva> findByHospedeId(Long hospedeId){
		return repository.findByHospedeId(hospedeId);
	}

	public List<Reserva> findByDataEntrada(Date dataEntrada){
		return repository.findByDataEntrada(dataEntrada);
	}
	
	public List<Reserva> findByDataSaida(Date dataSaida){
		return repository.findByDataSaida(dataSaida);
	}	
}