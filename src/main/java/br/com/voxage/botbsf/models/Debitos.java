package br.com.voxage.botbsf.models;

public class Debitos {
	private String possuiDeb;
	private DebitoAberto aberto;
	private String avencer;
	private String datavencer;
	private String ultimopag;
	public String getAvencer() {
		return avencer;
	}
	public void setAvencer(String avencer) {
		this.avencer = avencer;
	}
	public DebitoAberto getAberto() {
		return aberto;
	}
	public void setAberto(DebitoAberto aberto) {
		this.aberto = aberto;
	}
	public String getPossuiDeb() {
		return possuiDeb;
	}
	public void setPossuiDeb(String possuiDeb) {
		this.possuiDeb = possuiDeb;
	}
	public String getDatavencer() {
		return datavencer;
	}
	public void setDatavencer(String datavencer) {
		this.datavencer = datavencer;
	}
	public String getUltimopag() {
		return ultimopag;
	}
	public void setUltimopag(String ultimopag) {
		this.ultimopag = ultimopag;
	}	
}