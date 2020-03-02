package br.com.voxage.botbsf.models;

import java.util.ArrayList;

public class Planos {
	private String nome;
	private Serasa serasa;
	private ArrayList<Debitos> debitos;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Serasa getSerasa() {
		return serasa;
	}
	
	public void setSerasa(Serasa serasa) {
		this.serasa = serasa;
	}
	
	public ArrayList<Debitos> getDebitos() {
		return debitos;
	}
	
	public void setDebitos(ArrayList<Debitos> debitos) {
		this.debitos = debitos;
	}
}
