package br.com.voxage.botbsf.states.global;


import java.util.HashMap;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class FAQ {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("FAQ");
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.REDIRECT;
				
				return botStateFlow;
			});

			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.REDIRECT, "#REDIRECT");
			}});
		}};
	}
}
