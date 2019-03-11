package br.com.voxage.botbsf.models;

public class ConsultaCPF {
	private String dataNascimento;
	private String numeroProtocolo;
	private String statusBenef;
	private String tipoUsuario;
	private Cesta cesta;
	private Beneficio benef;
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getProtocolo() {
		return numeroProtocolo;
	}
	
	public void setProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	
	public String getStatusProt() {
		return statusBenef;
	}
	
	public void setStatusProt(String statusBenef) {
		this.statusBenef = statusBenef;
	}
	
	public String getUsuario() {
		return tipoUsuario;
	}
	
	public void setUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public Cesta getCesta() {
		return cesta;
	}
	
	public void setCesta(Cesta cesta) {
		this.cesta = cesta;
	}
	
	public Beneficio getBenef() {
		return benef;
	}
	
	public void setBenef(Beneficio benef) {
		this.benef = benef;
	}

	@Override
	public String toString() {
		return "ConsultaCPF [dataNascimento=" + dataNascimento + ", numeroProtocolo=" + numeroProtocolo
				+ ", tipoUsuario=" + tipoUsuario +", cesta=" + cesta + ", benef=" + benef + "]";
	}
}
