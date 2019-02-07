package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SemSerasa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEMSERASA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.NDCONSTA, "#NDCONSTA");
				put(BotBSF.STATES.REGULARIZADO, "#REGULARIZADO");				
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
