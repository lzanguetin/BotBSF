package br.com.voxage.botbsf.models;

import java.util.List;

public class DebitoAberto {
	private String plano;
	private List<String> debitos;
	
	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}
	
	public List<String> getVencimentos() {
		return debitos;
	}

	public void setVencimentos(List<String> debitos) {
		this.debitos = debitos;
	}

	@Override
	public String toString() {
		return "DebitoAberto [plano=" + plano + ", debitos=" + debitos + "]";
	}
}






