package br.com.voxage.botbsf.models;

public class Cartao {
	private String situacao;
	private Integer protocoloAtivacao;
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public Integer getProtocoloAtivacao() {
		return protocoloAtivacao;
	}
	
	public void setProtocoloAtivacao(Integer protocoloAtivacao) {
		this.protocoloAtivacao = protocoloAtivacao;
	}
}
