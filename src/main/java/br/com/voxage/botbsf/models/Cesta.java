package br.com.voxage.botbsf.models;

public class Cesta {
	private String possuiCestaAReceber;
	private String numero;
	private String dataRecebimento;
	
	public String getCestaReceber() {
		return possuiCestaAReceber;
	}
	
	public void setCestaReceber(String possuiCestaAReceber) {
		this.possuiCestaAReceber = possuiCestaAReceber;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getDataRecebimento() {
		return dataRecebimento;
	}
	
	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	@Override
	public String toString() {
		return "Cesta [possuiCestaAReceber=" + possuiCestaAReceber + ", numero=" + numero + ", dataRecebimento="
				+ dataRecebimento + "]";
	}
}
