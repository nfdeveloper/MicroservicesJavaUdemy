package io.github.nfdeveloper.mc.msavaliadorcredito.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.DadosCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.nfdeveloper.mc.msavaliadorcredito.infra.clients.ClienteResourceClient;

@Service
public class AvaliadorCreditoService {

	@Autowired
	private ClienteResourceClient clientesClient;
	@Autowired
	private CartoesResourceClient cartoesClient;
	
 	public SituacaoCliente obterSituacaoCliente(String cpf) {
		ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClientes(cpf);
		ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
		
		return new SituacaoCliente(dadosClienteResponse.getBody(), cartoesResponse.getBody());
	}
}
