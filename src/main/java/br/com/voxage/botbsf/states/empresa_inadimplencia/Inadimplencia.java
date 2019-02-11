package br.com.voxage.botbsf.states.empresa_inadimplencia;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inadimplencia {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADIMPLENCIA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				
				switch(dadosFluxo.getOperador()) {
				case "1":
					if(dadosFluxo.getDebito() == "1") {
						botStateFlow.navigationKey = "INADATIVO";
					}else if(dadosFluxo.getVencer() == "1") {
						botStateFlow.navigationKey = "INADVENCER";
					}else {
						botStateFlow.navigationKey = "SEMINAD";
					}
					break;
				case "2":
						botStateFlow.navigationKey = "INADINATIVO";
						break;
				case "3":
						botStateFlow.navigationKey = "INADSCADASTRO";
						break;
				default:
						botStateFlow.navigationKey = "TERMINATE";
				}
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("INADATIVO", "#INADATIVO");
				put("INADVENCER", "#INADVENCER");
				put("SEMINAD", "#SEMINAD");
				put("INADINATIVO", "#INADINATIVO");
				put("INADSCADASTRO", "#INADSCADASTRO");
				put("TERMINATE", "/TERMINATE");
			}});
		}};
	}
}