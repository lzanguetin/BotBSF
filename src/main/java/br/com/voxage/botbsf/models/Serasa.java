package br.com.voxage.botbsf.models;

public class Serasa {
	private String possuiSerasa;
	private String possuiPre;
	private String dataPre;
	private String dataRetirada;
	
	public String getSerasa() {
		return possuiSerasa;
	}

	public void setSerasa(String possuiSerasa) {
		this.possuiSerasa = possuiSerasa;
	}

	public String getPre() {
		return possuiPre;
	}

	public void setPre(String possuiPre) {
		this.possuiPre = possuiPre;
	}

	public String getDataPre() {
		return dataPre;
	}

	public void setDataPre(String dataPre) {
		this.dataPre = dataPre;
	}
	
	public String getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(String dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
}