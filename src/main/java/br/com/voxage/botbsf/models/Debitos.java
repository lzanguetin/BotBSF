package br.com.voxage.botbsf.models;

public class Debitos {
	private String situacao;
	private String dataPagamento;
	private Boolean possuiBoletoImpresso;
	private Impressao impressao;
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public Boolean getPossuiBoletoImpresso() {
		return possuiBoletoImpresso;
	}
	
	public void setPossuiBoletoImpresso(Boolean possuiBoletoImpresso) {
		this.possuiBoletoImpresso = possuiBoletoImpresso;
	}
	
	public Impressao getImpressao() {
		return impressao;
	}
	
	public void setImpressao(Impressao impressao) {
		this.impressao = impressao;
	}
}