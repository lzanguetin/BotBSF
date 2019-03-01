package br.com.voxage.botbsf.models;

import br.com.voxage.botbsf.models.Debitos;

public class ConsultaCNPJ {
	private String nomeEmpresa;
	private String emailResponsavel;
	private String qtdContratos;
	private Debitos debitos;
	private Impressao impressao;
	private Serasa serasa;
	
	public String getNome() {
		return nomeEmpresa;
	}
	
	public void setNome(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	public String getEmail() {
		return emailResponsavel;
	}

	public void setEmail(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}
	
	public String getContratos() {
		return qtdContratos;
	}
	
	public void setContratos(String qtdContratos) {
		this.qtdContratos = qtdContratos;
	}		
	
	public void setDebitos(Debitos debitos) {
		this.debitos = debitos;
	}
	
	public Debitos getDebitos() {
		return this.debitos;
	}
	
	public Impressao getImpressao() {
		return impressao;
	}

	public void setImpressao(Impressao impressao) {
		this.impressao = impressao;
	}
	
	public Serasa getSerasa() {
		return serasa;
	}

	public void setSerasa(Serasa serasa) {
		this.serasa = serasa;
	}

	@Override
	public String toString() {
		return "ConsultaCNPJ [nomeEmpresa=" + nomeEmpresa + ", emailResponsavel=" + emailResponsavel + ", debitos="
				+ debitos + ", impressao=" + impressao + ", serasa=" + serasa + "]";
	}
	
	
}