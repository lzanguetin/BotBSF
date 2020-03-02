package br.com.voxage.botbsf.models;

import java.util.ArrayList;

public class DadosBeneficiario {
	private String tipo;
	private String cpfBeneficiario;
	private String nomeBeneficiario;
	private String cpfTrabalhador;
	private String nomeTrabalhador;
	private String dataNascimento;
	private String email;
	private String telefoneCelular;
	private String nomeMae;
	private ArrayList<Cartao> cartaoBeneficios;
	private ArrayList<Cestas> cestas;
	private ArrayList<Beneficios> beneficios;
	
	public String getCpfBeneficiario() {
		return cpfBeneficiario;
	}
	
	public void setCpfBeneficiario(String cpfBeneficiario) {
		this.cpfBeneficiario = cpfBeneficiario;
	}
	
	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}
	
	public void setNomeBeneficiario(String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}
	
	public String getCpfTrabalhador() {
		return cpfTrabalhador;
	}
	
	public void setCpfTrabalhador(String cpfTrabalhador) {
		this.cpfTrabalhador = cpfTrabalhador;
	}
	
	public String getNomeTrabalhador() {
		return nomeTrabalhador;
	}
	
	public void setNomeTrabalhador(String nomeTrabalhador) {
		this.nomeTrabalhador = nomeTrabalhador;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefoneCelular() {
		return telefoneCelular;
	}
	
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	
	public String getNomeMae() {
		return nomeMae;
	}
	
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	public ArrayList<Cartao> getCartaoBeneficios() {
		return cartaoBeneficios;
	}
	
	public void setCartaoBeneficios(ArrayList<Cartao> cartaoBeneficios) {
		this.cartaoBeneficios = cartaoBeneficios;
	}
	
	public ArrayList<Cestas> getCestas() {
		return cestas;
	}
	
	public void setCestas(ArrayList<Cestas> cestas) {
		this.cestas = cestas;
	}
	
	public ArrayList<Beneficios> getBeneficios() {
		return beneficios;
	}
	
	public void setBeneficios(ArrayList<Beneficios> beneficios) {
		this.beneficios = beneficios;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
