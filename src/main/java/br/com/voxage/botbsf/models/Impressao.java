package br.com.voxage.botbsf.models;

public class Impressao {
	private String possuiImpressao;
	private String data;
	private String vencimento;
	private String horasParaPagamento;

	public String getPossuiImp() {
		return possuiImpressao;
	}

	public void setPossuiImp(String possuiImpressao) {
		this.possuiImpressao = possuiImpressao;
	}

	public String getUltimaImp() {
		return data;
	}

	public void setUltimaImp(String data) {
		this.data = data;
	}
	
	public String getVencUltima() {
		return vencimento;
	}

	public void setVencUltima(String vencimento) {
		this.vencimento = vencimento;
	}
	
	public String getHrsPagamento() {
		return horasParaPagamento;
	}

	public void setHrsPagamento(String horasParaPagamento) {
		this.horasParaPagamento = horasParaPagamento;
	}

	@Override
	public String toString() {
		return "Impressao [possuiImpressao=" + possuiImpressao + ", data=" + data + ", vencimento=" + vencimento
				+ ", horasParaPagamento=" + horasParaPagamento + "]";
	}

	
}





