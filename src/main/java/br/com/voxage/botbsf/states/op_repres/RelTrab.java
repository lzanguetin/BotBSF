package br.com.voxage.botbsf.states.representante;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class RelTrab {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("RELTRAB");

				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

				setPreFunction(botState ->{
					bot.setLastState(BotBSF.STATES.REPRESENTANTE);
					
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
                	BotStateFlow botStateFlow = new BotStateFlow();
                	botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
                	botStateFlow.navigationKey = "TERMINATE";
                	
                	return botStateFlow;
                });
                
                setNextNavigationMap(new HashMap<String, String>(){{
                	put("TERMINATE", "#TERMINATE");
                }});
		}};
	}
}
