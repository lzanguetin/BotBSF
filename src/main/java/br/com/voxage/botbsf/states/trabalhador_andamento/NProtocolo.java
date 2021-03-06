package br.com.voxage.botbsf.states.trabalhador_andamento;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class NProtocolo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("NPROTOCOLO");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					if(bot.getFirstTime() == 0) {
						botStateFlow.navigationKey = BotBSF.STATES.ANDAMENTO;
					}else {
						botStateFlow.navigationKey = BotBSF.STATES.ATENDENTE;
					}
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{
					put(BotBSF.STATES.ANDAMENTO, "#ANDAMENTO");
                    put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
				}});
		}};
	}
}