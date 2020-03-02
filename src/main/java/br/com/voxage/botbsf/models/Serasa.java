package br.com.voxage.botbsf.models;

public class Serasa {
	private String situacao;
	private String dataEntrada;
	private String dataParaRegularizacao;
	private String dataRetirada;
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getDataEntrada() {
		return dataEntrada;
	}
	
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	public String getDataParaRegularizacao() {
		return dataParaRegularizacao;
	}
	
	public void setDataParaRegularizacao(String dataParaRegularizacao) {
		this.dataParaRegularizacao = dataParaRegularizacao;
	}
	
	public String getDataRetirada() {
		return dataRetirada;
	}
	
	public void setDataRetirada(String dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
}