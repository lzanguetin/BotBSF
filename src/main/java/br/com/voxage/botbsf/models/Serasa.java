package br.com.voxage.botbsf.models;

public class Serasa {
	private String posuiCadastroSerasa;
	private String posuiCadastroPreSerasa;
	private String dataPreSerasa;
	private String dataRetiradaSerasa;
	
	public String getSerasa() {
		return posuiCadastroSerasa;
	}

	public void setSerasa(String posuiCadastroSerasa) {
		this.posuiCadastroSerasa = posuiCadastroSerasa;
	}

	public String getPre() {
		return posuiCadastroPreSerasa;
	}

	public void setPre(String posuiCadastroPreSerasa) {
		this.posuiCadastroPreSerasa = posuiCadastroPreSerasa;
	}

	public String getdataPreSerasa() {
		return dataPreSerasa;
	}

	public void setdataPreSerasa(String dataPreSerasa) {
		this.dataPreSerasa = dataPreSerasa;
	}
	
	public String getdataRetiradaSerasa() {
		return dataRetiradaSerasa;
	}

	public void setdataRetiradaSerasa(String dataRetiradaSerasa) {
		this.dataRetiradaSerasa = dataRetiradaSerasa;
	}

	@Override
	public String toString() {
		return "Serasa [posuiCadastroSerasa=" + posuiCadastroSerasa + ", posuiCadastroPreSerasa="
				+ posuiCadastroPreSerasa + ", dataPreSerasa=" + dataPreSerasa + ", dataRetiradaSerasa="
				+ dataRetiradaSerasa + "]";
	}
	
}