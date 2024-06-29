package io.github.nfdeveloper.mc.msavaliadorcredito.application.exception;

public class ErroComunicacaoMicroservicesException extends Exception{
	
	private Integer status;
	
	public ErroComunicacaoMicroservicesException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}	
	
}
