package br.com.douglas.hotel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.douglas.hotel.domain.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
	List<Reserva> findByDataEntrada(Date dataEntrada);
	List<Reserva> findByDataSaida(Date dataSaida);
	List<Reserva> findByHospedeId(Long hospedeId);
}