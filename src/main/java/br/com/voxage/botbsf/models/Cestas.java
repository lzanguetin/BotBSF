package br.com.voxage.botbsf.models;

import java.util.Date;

public class Cestas {
	private Integer protocolo; 
	private String situacao; 
	private Integer numero;
	private Date dataEntrega;
	private Date dataPrevista;
	
	public Integer getProtocolo() {
		return protocolo;
	}
	
	public void setProtocolo(Integer protocolo) {
		this.protocolo = protocolo;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Date getDataEntrega() {
		return dataEntrega;
	}
	
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	public Date getDataPrevista() {
		return dataPrevista;
	}
	
	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}
}
