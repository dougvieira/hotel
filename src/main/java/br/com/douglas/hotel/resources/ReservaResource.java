package br.com.douglas.hotel.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.hotel.domain.Hospede;
import br.com.douglas.hotel.domain.Reserva;
import br.com.douglas.hotel.service.HospedeService;
import br.com.douglas.hotel.service.ReservaService;

@RestController
public class ReservaResource {
	private static final String HORA_CHECKOUT = "16:30";
	private static final Double DIARIA_SEMANA = 120.00;
	private static final Double DIARIA_FDS = 150.00;
	private static final Double VAGA_SEMANA = 15.00;
	private static final Double VAGA_FDS = 20.00;
	
	@Autowired
	private ReservaService service;
	
	@Autowired
	private HospedeService hService;
	
	@GetMapping(path="/checkIn")	
	public ResponseEntity<List<Reserva>> findAllCheckIn(){
		List<Reserva> reservas = service.findByDataSaida(null);
		for(Reserva reserva : reservas) {
			reserva.setValor(calculoHospedagem(reserva.getDataEntrada(), Calendar.getInstance().getTime(), reserva.isAdicionalVeiculo()));
		}
		return ResponseEntity.ok().body(reservas);
	}
	
	@GetMapping(path="/checkOut")	
	public ResponseEntity<List<Reserva>> findAllCheckOut(){
		List<Reserva> reservas = service.findAll();
		List<Reserva> checkOut = new ArrayList<Reserva>();
		for(Reserva reserva : reservas) {
			if(reserva.getDataSaida() != null ){;
				reserva.setValor(calculoHospedagem(reserva.getDataEntrada(), reserva.getDataSaida(), reserva.isAdicionalVeiculo()));
				checkOut.add(reserva);
			}
		}
		return ResponseEntity.ok().body(checkOut);
	}
	
	@PostMapping(path="/checkIn", consumes="application/json", produces="application/json")
	public void addHospede(@RequestBody Reserva reserva) {	
		Hospede hospede = hService.findByDocument(reserva.getHospede().getDocumento());
		
		if(reserva.getDataSaida() != null) {
			reserva.setValor(calculoHospedagem(reserva.getDataEntrada(), reserva.getDataSaida(), reserva.isAdicionalVeiculo()));
		}

		if(hospede != null) {
			reserva.setHospede(hospede);			
		}
		service.save(reserva);
	}	

	private Double calculoHospedagem(Date dataEntrada, Date dataSaida, boolean adicionalVeiculo) {
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		double valor = 0.0;
		Date data = dataEntrada;
		
		while(data.before(dataSaida)) {			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			
			valor = valorDiaria(calendar, adicionalVeiculo, valor);
			calendar.add(Calendar.DATE, 1);
			data = calendar.getTime();
		}
		
		try {
			String formatHora = sdfHora.format(dataSaida);
			Date horaSaida = sdfHora.parse(formatHora);
			Date horaLimite = sdfHora.parse(HORA_CHECKOUT);
			if(horaSaida.after(horaLimite)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dataSaida);
				valor = valorDiaria(calendar, adicionalVeiculo, valor);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}						
		
		return valor;
	}

	private double valorDiaria(Calendar calendar, boolean adicionalVeiculo, double valor) {
		if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			valor += DIARIA_FDS;
			if(adicionalVeiculo) {
				valor += VAGA_FDS;
			}
		} else {
			valor += DIARIA_SEMANA;
			if(adicionalVeiculo) {
				valor += VAGA_SEMANA;
			}
		}
		return valor;
	}
}