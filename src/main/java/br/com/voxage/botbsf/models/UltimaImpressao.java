package br.com.voxage.botbsf.models;

import java.util.Date;

public class UltimaImpressao {
	private Date dataHora;
	private Date dataVencimento;
	private Integer qtdHorasDePagamento;
	
	public Date getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	public Date getDataVencimento() {
		return dataVencimento;
	}
	
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public Integer getQtdHorasDePagamento() {
		return qtdHorasDePagamento;
	}
	
	public void setQtdHorasDePagamento(Integer qtdHorasDePagamento) {
		this.qtdHorasDePagamento = qtdHorasDePagamento;
	}
}





