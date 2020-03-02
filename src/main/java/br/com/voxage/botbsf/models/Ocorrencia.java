package br.com.voxage.botbsf.models;

public class Ocorrencia {
	private String cpf;
	private String cnpj;
	private String canal;
	private String menu;
	private String descricao;
	private String dataHora;
	private Integer callId;
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getCanal() {
		return canal;
	}
	
	public void setCanal(String canal) {
		this.canal = canal;
	}
	
	public String getMenu() {
		return menu;
	}
	
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	
	public Integer getCallId() {
		return callId;
	}
	
	public void setCallId(Integer callId) {
		this.callId = callId;
	}
}
