package io.github.nfdeveloper.mc.msavaliadorcredito.domain.model;

import java.math.BigDecimal;

public class Cartao {

	private Long id;
	private String nome;
	private String bandeira;
	private BigDecimal limiteBasico;
	
	public Cartao() { }

	public Cartao(Long id, String nome, String bandeira, BigDecimal limiteBasico) {
		this.id = id;
		this.nome = nome;
		this.bandeira = bandeira;
		this.limiteBasico = limiteBasico;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getLimiteBasico() {
		return limiteBasico;
	}

	public void setLimiteBasico(BigDecimal limiteBasico) {
		this.limiteBasico = limiteBasico;
	}
	
	
}
