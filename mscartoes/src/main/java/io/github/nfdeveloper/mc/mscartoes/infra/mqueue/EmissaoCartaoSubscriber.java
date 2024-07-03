package io.github.nfdeveloper.mc.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.nfdeveloper.mc.mscartoes.domain.Cartao;
import io.github.nfdeveloper.mc.mscartoes.domain.ClienteCartao;
import io.github.nfdeveloper.mc.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.nfdeveloper.mc.mscartoes.infra.repository.CartaoRepository;
import io.github.nfdeveloper.mc.mscartoes.infra.repository.ClienteCartaoRepository;

@Component
public class EmissaoCartaoSubscriber {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		
		try {
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
