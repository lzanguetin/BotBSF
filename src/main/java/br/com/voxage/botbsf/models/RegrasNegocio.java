package br.com.voxage.botbsf.models;

import java.util.Date;

public class RegrasNegocio {
    private Integer qtdPlanos;   
    private Boolean possuiImpressaoBoleto;
    private Boolean possuiDebitos;
    private Boolean possuiCadastroSerasa;
    private Boolean possuiCadastroPreSerasa;
    private Boolean possuiMaisDeUmCadastroSerasaOuPreSerasa;
    private Boolean possuiApenasUmBoletoAVencer;
    private UltimaImpressao ultimaImpressao;
    private Date dataUltimoPagamento;
    private Date dataVencimentoUnicoBoletoAVencer;
    private Date dataParaRegularizacaoPreSerasa;
    private Date dataRetiradaSerasa;
	
    public Integer getQtdPlanos() {
		return qtdPlanos;
	}
	
    public void setQtdPlanos(Integer qtdPlanos) {
		this.qtdPlanos = qtdPlanos;
	}
	
    public Boolean getPossuiImpressaoBoleto() {
		return possuiImpressaoBoleto;
	}
	
    public void setPossuiImpressaoBoleto(Boolean possuiImpressaoBoleto) {
		this.possuiImpressaoBoleto = possuiImpressaoBoleto;
	}
	
    public Boolean getPossuiDebitos() {
		return possuiDebitos;
	}
	
    public void setPossuiDebitos(Boolean possuiDebitos) {
		this.possuiDebitos = possuiDebitos;
	}
	
    public Boolean getPossuiCadastroSerasa() {
		return possuiCadastroSerasa;
	}
	
    public void setPossuiCadastroSerasa(Boolean possuiCadastroSerasa) {
		this.possuiCadastroSerasa = possuiCadastroSerasa;
	}
	
    public Boolean getPossuiCadastroPreSerasa() {
		return possuiCadastroPreSerasa;
	}
	
    public void setPossuiCadastroPreSerasa(Boolean possuiCadastroPreSerasa) {
		this.possuiCadastroPreSerasa = possuiCadastroPreSerasa;
	}
	
    public Boolean getPossuiMaisDeUmCadastroSerasaOuPreSerasa() {
		return possuiMaisDeUmCadastroSerasaOuPreSerasa;
	}
	
    public void setPossuiMaisDeUmCadastroSerasaOuPreSerasa(Boolean possuiMaisDeUmCadastroSerasaOuPreSerasa) {
		this.possuiMaisDeUmCadastroSerasaOuPreSerasa = possuiMaisDeUmCadastroSerasaOuPreSerasa;
	}
	
    public Boolean getPossuiApenasUmBoletoAVencer() {
		return possuiApenasUmBoletoAVencer;
	}
	
    public void setPossuiApenasUmBoletoAVencer(Boolean possuiApenasUmBoletoAVencer) {
		this.possuiApenasUmBoletoAVencer = possuiApenasUmBoletoAVencer;
	}
	
    public UltimaImpressao getUltimaImpressao() {
		return ultimaImpressao;
	}
	
    public void setUltimaImpressao(UltimaImpressao ultimaImpressao) {
		this.ultimaImpressao = ultimaImpressao;
	}
	
    public Date getDataVencimentoUnicoBoletoAVencer() {
		return dataVencimentoUnicoBoletoAVencer;
	}
	
    public void setDataVencimentoUnicoBoletoAVencer(Date dataVencimentoUnicoBoletoAVencer) {
		this.dataVencimentoUnicoBoletoAVencer = dataVencimentoUnicoBoletoAVencer;
	}
	
    public Date getDataParaRegularizacaoPreSerasa() {
		return dataParaRegularizacaoPreSerasa;
	}
	
    public void setDataParaRegularizacaoPreSerasa(Date dataParaRegularizacaoPreSerasa) {
		this.dataParaRegularizacaoPreSerasa = dataParaRegularizacaoPreSerasa;
	}
	
    public Date getDataRetiradaSerasa() {
		return dataRetiradaSerasa;
	}
	
    public void setDataRetiradaSerasa(Date dataRetiradaSerasa) {
		this.dataRetiradaSerasa = dataRetiradaSerasa;
	}

	public Date getDataUltimoPagamento() {
		return dataUltimoPagamento;
	}

	public void setDataUltimoPagamento(Date dataUltimoPagamento) {
		this.dataUltimoPagamento = dataUltimoPagamento;
	}
}