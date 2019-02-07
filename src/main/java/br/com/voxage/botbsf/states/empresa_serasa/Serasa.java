package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Serasa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SERASA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.COMSERASA, "#COMSERASA");
				put(BotBSF.STATES.SEMSERASA, "#SEMSERASA");
				put(BotBSF.STATES.PRESERASA, "#PRESERASA");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
