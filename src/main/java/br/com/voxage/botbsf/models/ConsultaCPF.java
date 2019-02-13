package br.com.voxage.botbsf.models;

public class ConsultaCPF {
	private String dataNascimento;
	private String protocoloValidacao;
	private Cesta cesta;
	private Beneficio benef;
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getProtocoloValidacao() {
		return protocoloValidacao;
	}
	
	public void setProtocoloValidacao(String protocoloValidacao) {
		this.protocoloValidacao = protocoloValidacao;
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
		return "ConsultaCPF [dataNascimento=" + dataNascimento + ", protocoloValidacao=" + protocoloValidacao
				+ ", cesta=" + cesta + ", benef=" + benef + "]";
	}
}
