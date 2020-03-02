package br.com.voxage.botbsf.models;

import java.util.ArrayList;

public class Beneficios {
	private String nome;
	private Integer protocolo;
	private String situacao;
	private String dataSituacao;
	private String detalhamento;
	private ArrayList<Historico> historico;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
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
	
	public String getDataSituacao() {
		return dataSituacao;
	}
	
	public void setDataSituacao(String dataSituacao) {
		this.dataSituacao = dataSituacao;
	}
	
	public String getDetalhamento() {
		return detalhamento;
	}
	
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	
	public ArrayList<Historico> getHistorico() {
		return historico;
	}
	
	public void setHistorico(ArrayList<Historico> historico) {
		this.historico = historico;
	}
}