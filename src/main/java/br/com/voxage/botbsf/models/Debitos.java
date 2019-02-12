package br.com.voxage.botbsf.models;

public class Debitos {
	private String possuiDebitos;
	private DebitoAberto debitosEmAberto;
	private String soPossuiBoletoAVencer;
	private String dataVencimentoBoletoAVencer;
	private String dataUltimoPagamento;
	
	public String getPossuiDeb() {
		return possuiDebitos;
	}
	public void setPossuiDeb(String possuiDebitos) {
		this.possuiDebitos = possuiDebitos;
	}
	
	public String getAvencer() {
		return soPossuiBoletoAVencer;
	}
	public void setAvencer(String soPossuiBoletoAVencer) {
		this.soPossuiBoletoAVencer = soPossuiBoletoAVencer;
	}

	public DebitoAberto getdebitosEmAberto() {
		return debitosEmAberto;
	}
	public void setdebitosEmAberto(DebitoAberto debitosEmAberto) {
		this.debitosEmAberto = debitosEmAberto;
	}
	public String getdataVencimentoBoletoAVencer() {
		return dataVencimentoBoletoAVencer;
	}
	public void setdataVencimentoBoletoAVencer(String dataVencimentoBoletoAVencer) {
		this.dataVencimentoBoletoAVencer = dataVencimentoBoletoAVencer;
	}
	public String getdataUltimoPagamento() {
		return dataUltimoPagamento;
	}
	public void setdataUltimoPagamento(String dataUltimoPagamento) {
		this.dataUltimoPagamento = dataUltimoPagamento;
	}
	@Override
	public String toString() {
		return "Debitos [possuiDebitos=" + possuiDebitos + ", soPossuiBoletosAVencer=" + soPossuiBoletoAVencer
				+ ", debitosEmAberto=" + debitosEmAberto + ", dataVencimentoBoletoAVencer=" + dataVencimentoBoletoAVencer + ", dataUltimoPagamento=" + dataUltimoPagamento + "]";
	}
	
}