package io.github.nfdeveloper.mc.msclientes.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nfdeveloper.mc.msclientes.domain.Cliente;
import io.github.nfdeveloper.mc.msclientes.infra.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Optional<Cliente> getByCPF(String cpf){
		return repository.findByCpf(cpf);
	}
	
}
