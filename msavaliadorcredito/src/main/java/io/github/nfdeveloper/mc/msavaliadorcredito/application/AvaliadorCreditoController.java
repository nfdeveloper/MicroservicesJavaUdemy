package io.github.nfdeveloper.mc.msavaliadorcredito.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.nfdeveloper.mc.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.nfdeveloper.mc.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
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
	public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dado) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
								.realizarAvaliacao(dado.getCpf(), dado.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
}
