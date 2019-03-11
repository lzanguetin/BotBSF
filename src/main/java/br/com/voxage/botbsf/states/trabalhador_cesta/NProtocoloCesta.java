package br.com.voxage.botbsf.states.trabalhador_cesta;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class NProtocoloCesta {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("NPROTOCOLOCESTA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "ATENDENTE";
					
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("ATENDENTE", "/ATENDENTE");
			}});
		}};
	}
}