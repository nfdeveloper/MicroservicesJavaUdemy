package io.github.nfdeveloper.mc.msavaliadorcredito.domain.model;

public class DadosAvaliacao {
	private String cpf;
	private Long renda;
	
	public DadosAvaliacao() { }

	public DadosAvaliacao(String cpf, Long renda) {
		this.cpf = cpf;
		this.renda = renda;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getRenda() {
		return renda;
	}

	public void setRenda(Long renda) {
		this.renda = renda;
	}
	
	
	
}
