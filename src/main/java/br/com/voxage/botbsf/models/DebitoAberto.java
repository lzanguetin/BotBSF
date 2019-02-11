package br.com.voxage.botbsf.models;

import java.util.List;

public class DebitoAberto {
	private String plano;
	private List<String> vencimentos;
	
	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}
	
	public List<String> getVencimentos() {
		return vencimentos;
	}

	public void setVencimentos(List<String> vencimentos) {
		this.vencimentos = vencimentos;
	}
}






