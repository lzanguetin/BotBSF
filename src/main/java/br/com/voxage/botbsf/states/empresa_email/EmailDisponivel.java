package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class EmailDisponivel {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("EMAILDISPONIVEL");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = "FINALIZARINAD";
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{
					put("FINALIZARINAD", "#FINALIZARINAD");
				}});
		}};
	}
}
