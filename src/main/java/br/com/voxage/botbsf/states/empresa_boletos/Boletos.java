package br.com.voxage.botbsf.states.empresa_boletos;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
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
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if((consulta.getImpressao().getPossuiImp()) == "true") {
					botStateFlow.navigationKey = "BOLETOIMPRESSO";
				}else if((consulta.getImpressao().getPossuiImp()) == "false") {
					botStateFlow.navigationKey = "SEMBOLETOS";
				}
				else {
					botStateFlow.navigationKey = "ATENDENTE";
				}
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
				put(BotBSF.STATES.SEMBOLETOS, "#SEMBOLETOS");
				put(BotBSF.STATES.BOLETOIMPRESSO, "#BOLETOIMPRESSO");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
