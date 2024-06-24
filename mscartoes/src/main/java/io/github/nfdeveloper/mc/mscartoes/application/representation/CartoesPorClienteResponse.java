package io.github.nfdeveloper.mc.mscartoes.application.representation;

import java.math.BigDecimal;

import io.github.nfdeveloper.mc.mscartoes.domain.ClienteCartao;

public class CartoesPorClienteResponse {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
		
	public CartoesPorClienteResponse() { }

	public CartoesPorClienteResponse(String nome, String bandeira, BigDecimal limiteLiberado) {
		this.nome = nome;
		this.bandeira = bandeira;
		this.limiteLiberado = limiteLiberado;
	}
	
	public static CartoesPorClienteResponse fromModel(ClienteCartao model) {
		return new CartoesPorClienteResponse(
				model.getCartao().getNome(), 
				model.getCartao().getBandeira().toString(), 
				model.getLimite()
				);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public BigDecimal getlimiteLiberado() {
		return limiteLiberado;
	}

	public void setlimiteLiberado(BigDecimal limiteLiberado) {
		this.limiteLiberado = limiteLiberado;
	}
	
}
