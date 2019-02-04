package br.com.voxage.botbsf.models;

import br.com.voxage.vbot.BotInputResult;

public class DadosFluxo {
	private String option;
	private String type;
	private String cnpj;
	private String faq;
	private String local;
	private String contato;
	private String cpf;
	private String operador;
	private BotInputResult botInputResult;
    
	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}
	
	public String getCPF() {
		return cpf;
	}

	public void setCPF(String cpf) {
		this.cpf = cpf;
	} 
	
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}  
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}  
	
	public String getFAQ() {
		return faq;
	}

	public void setFAQ(String faq) {
		this.faq = faq;
	} 
	
	public String getCNPJ() {
		return cnpj;
	}

	public void setCNPJ(String cnpj) {
		this.cnpj = cnpj;
	} 
	
    public String getType() {
    	return type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }  
	
    public String getOption() {
    	return option;
    }
    
    public void setOption(String option) {
    	this.option = option;
    }  
    
    public BotInputResult getBotInputResult() {
        return botInputResult;
    }

    public void setBotInputResult(BotInputResult botInputResult) {
        this.botInputResult = botInputResult;
    }
}