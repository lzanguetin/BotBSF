package br.com.voxage.botbsf.states.trabalhador_andamento;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Potocolo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("PROTOCOLO");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					ConsultaCPF consulta = bot.getConsultaCPF();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					botState.setCustomField("beneficio", consulta.getStatusProt());
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = "FINALIZAR";
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put("FINALIZAR", "#FINALIZAR");
				}});
		}};
	}
}