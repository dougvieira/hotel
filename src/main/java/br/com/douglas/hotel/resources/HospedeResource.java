package br.com.douglas.hotel.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.douglas.hotel.domain.Hospede;
import br.com.douglas.hotel.service.HospedeService;

@RestController
@RequestMapping(value="/hospedes")
public class HospedeResource {

	@Autowired
	private HospedeService service;
	
	@GetMapping	
	public ResponseEntity<List<Hospede>> findAll(){
		List<Hospede> hospedes = service.findAll();
		return ResponseEntity.ok().body(hospedes);
	}
	
	@PostMapping(consumes="application/json", produces="application/json")
	public void addHospede(@RequestBody Hospede hospede) {		
		service.save(hospede);
	}
	
	@PutMapping(path="/{documento}")
	public void updateHospede(@PathVariable String documento, @RequestBody Hospede updHospede) {
		 Hospede hospede = service.findByDocument(documento);
		 hospede.setDocumento(updHospede.getDocumento());
		 hospede.setNome(updHospede.getNome());
		 hospede.setTelefone(updHospede.getTelefone());
		 
		 service.save(hospede);
	}	
	
	@DeleteMapping(path="/{documento}")
	public void deleteHospede(@PathVariable String documento) {
		Hospede hospede = service.findByDocument(documento);
		service.delete(hospede);
	}	
	
	@GetMapping(path="/nome/{nome}")
	public Hospede findByNome(@PathVariable String nome) {
		return service.findByNome(nome);
	}
	
	@GetMapping(path="/documento/{documento}")
	public Hospede findByDocument(@PathVariable String documento) {
		return service.findByDocument(documento);
	}
	
	@GetMapping(path="/telefone/{telefone}")
	public Hospede findByTelefone(@PathVariable String telefone) {
		return service.findByTelefone(telefone);
	}
}
