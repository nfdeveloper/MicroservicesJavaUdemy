package io.github.nfdeveloper.mc.msclientes.application;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.nfdeveloper.mc.msclientes.application.representation.ClienteSaveRequest;

@RestController
@RequestMapping("/clientes")
public class ClientesResource {
	
	@Autowired
	private ClienteService service;
	

	@PostMapping
	public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
		var cliente = request.toModel();
		service.save(cliente);
		URI headerLocation = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.query("cpf={cpf}")
					.buildAndExpand(cliente.getCpf())
					.toUri();
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping
	public ResponseEntity dadosCliente(@RequestParam("cpf")String cpf) {
		var cliente = service.getByCPF(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente);
	}
}
