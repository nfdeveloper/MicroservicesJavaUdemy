package io.github.nfdeveloper.mc.msavaliadorcredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.nfdeveloper.mc.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.nfdeveloper.mc.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.github.nfdeveloper.mc.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.Cartao;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.CartaoAprovado;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.DadosCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.nfdeveloper.mc.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.nfdeveloper.mc.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.nfdeveloper.mc.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;

@Service
public class AvaliadorCreditoService {

	@Autowired
	private ClienteResourceClient clientesClient;
	@Autowired
	private CartoesResourceClient cartoesClient;
	@Autowired
	private SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
	
 	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
 		try {
 			ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClientes(cpf);
 			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
 			
 			return new SituacaoCliente(dadosClienteResponse.getBody(), cartoesResponse.getBody());
			
		} catch (FeignException.FeignClientException e) {
				int status = e.status();
				if(HttpStatus.NOT_FOUND.value() == status) {
					throw new DadosClienteNotFoundException();
				}
				
				throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
 	
 	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
 		try {
 			ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClientes(cpf);
 			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);
			
 			List<Cartao> cartoes = cartoesResponse.getBody();
 			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
 				
 				DadosCliente dadosCliente = dadosClienteResponse.getBody();
 				
 				BigDecimal limiteBasico = cartao.getLimiteBasico();
 				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
 				var fator = idadeBD.divide(BigDecimal.valueOf(10));
 				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
 				
 				CartaoAprovado aprovado = new CartaoAprovado();
 				aprovado.setCartao(cartao.getNome());
 				aprovado.setBandeira(cartao.getBandeira());
 				aprovado.setLimiteAprovado(limiteAprovado);
 				
 				return aprovado;
 			}).collect(Collectors.toList());
 			
 			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
 			
		}  catch (FeignException.FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
 	}
 	
 	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
 		try {
			emissaoCartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString();
			return new ProtocoloSolicitacaoCartao(protocolo);
			
		} catch (Exception e) {
			throw new ErroSolicitacaoCartaoException(e.getMessage());
		}
 	}

}
