package io.github.nfdeveloper.mc.msavaliadorcredito.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.SituacaoCliente;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {
	
	@Autowired
	private AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status() {
		return "Ok";
	}
	
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
		return ResponseEntity.ok(situacaoCliente);
	}
}