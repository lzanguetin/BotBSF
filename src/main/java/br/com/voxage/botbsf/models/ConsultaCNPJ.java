package br.com.voxage.botbsf.models;

public class ConsultaCNPJ {
	private String nomeEmpresa;
	private String emailResponsavel;
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
	
	public Debitos getDebitos() {
		return debitos;
	}

	public void setDebitos(Debitos debitos) {
		this.debitos = debitos;
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
}