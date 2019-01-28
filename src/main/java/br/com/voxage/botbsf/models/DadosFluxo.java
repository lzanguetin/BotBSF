package br.com.voxage.botbsf.models;

import br.com.voxage.vbot.BotInputResult;

public class DadosFluxo {
	private String option;
	private BotInputResult botInputResult;
    
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
