package br.com.voxage.botbsf.models;

import java.util.List;

public class Beneficio {
	private String status; 
	private List<String> listaDocumentos;
	private String dataRecebimentoBeneficio; 
	private String nomeSindicatoAReceber;
	private String dataRecebimentoCartao;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getListaDocumentos() {
		return listaDocumentos;
	}
	
	public void setListaDocumentos(List<String> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}
	
	public String getDataRecebimentoBeneficio() {
		return dataRecebimentoBeneficio;
	}
	
	public void setDataRecebimentoBeneficio(String dataRecebimentoBeneficio) {
		this.dataRecebimentoBeneficio = dataRecebimentoBeneficio;
	}
	
	public String getNomeSindicatoAReceber() {
		return nomeSindicatoAReceber;
	}
	
	public void setNomeSindicatoAReceber(String nomeSindicatoAReceber) {
		this.nomeSindicatoAReceber = nomeSindicatoAReceber;
	}
	
	public String getDataRecebimentoCartao() {
		return dataRecebimentoCartao;
	}
	
	public void setDataRecebimentoCartao(String dataRecebimentoCartao) {
		this.dataRecebimentoCartao = dataRecebimentoCartao;
	}

	@Override
	public String toString() {
		return "Beneficio [status=" + status + ", listaDocumentos=" + listaDocumentos + ", dataRecebimentoBeneficio="
				+ dataRecebimentoBeneficio + ", nomeSindicatoAReceber=" + nomeSindicatoAReceber
				+ ", dataRecebimentoCartao=" + dataRecebimentoCartao + "]";
	}
}
