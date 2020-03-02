package br.com.voxage.botbsf.states.empresa_boletos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Boletos {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("BOLETOS");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState->{
				bot.setLastState(BotBSF.STATES.MENUEMPRESA);
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getRegrasNegocio().getPossuiImpressaoBoleto() == true) {
			    	DateFormat actualDate = new SimpleDateFormat("MM");
			    	DateFormat returnDate = new SimpleDateFormat("MM"); 
			    	Date date1 = new Date();
			    	Date date2 = new Date();
			    	date2 = consulta.getRegrasNegocio().getUltimaImpressao().getDataHora();
			    	
			    	if(actualDate.format(date1).equals(returnDate.format(date2))) {
			    		botStateFlow.navigationKey = BotBSF.STATES.BOLETOIMPRESSO;
			    	}else {
			    		botStateFlow.navigationKey = BotBSF.STATES.SEMBOLETOS;
			    	}
				}else if(consulta.getRegrasNegocio().getPossuiImpressaoBoleto() == false) {
					botStateFlow.navigationKey = BotBSF.STATES.SEMBOLETOS;
				}
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
				put(BotBSF.STATES.SEMBOLETOS, "#SEMBOLETOS");
				put(BotBSF.STATES.BOLETOIMPRESSO, "#BOLETOIMPRESSO");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
