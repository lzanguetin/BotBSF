package br.com.voxage.botbsf.models;

import java.util.ArrayList;

public class DadosEmpresa {
	private RegrasNegocio regrasNegocio;	
    private String cnpjEmpresa;
    private String nomeEmpresa;
    private String cpfEmpregador;
    private String nomeEmpregador;
	private String emailResponsavel;
	private Boolean totalCaged;
	private String videoImpressao;
    private ArrayList<Planos> planos;
    
    public String getCpfEmpregador() {
		return cpfEmpregador;
	}
	
    public void setCpfEmpregador(String cpfEmpregador) {
		this.cpfEmpregador = cpfEmpregador;
	}
	
    public String getNomeEmpregador() {
		return nomeEmpregador;
	}
	
    public void setNomeEmpregador(String nomeEmpregador) {
		this.nomeEmpregador = nomeEmpregador;
	}
    
	public RegrasNegocio getRegrasNegocio() {
		return regrasNegocio;
	}
	
	public void setRegrasNegocio(RegrasNegocio regrasNegocio) {
		this.regrasNegocio = regrasNegocio;
	}
	
	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}
	
	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	public String getEmailResponsavel() {
		return emailResponsavel;
	}
	
	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public ArrayList<Planos> getPlanos() {
		return planos;
	}

	public void setPlanos(ArrayList<Planos> planos) {
		this.planos = planos;
	}

	public Boolean getTotalCaged() {
		return totalCaged;
	}

	public void setTotalCaged(Boolean totalCaged) {
		this.totalCaged = totalCaged;
	}

	public String getVideoImpressao() {
		return videoImpressao;
	}

	public void setVideoImpressao(String videoImpressao) {
		this.videoImpressao = videoImpressao;
	}
}