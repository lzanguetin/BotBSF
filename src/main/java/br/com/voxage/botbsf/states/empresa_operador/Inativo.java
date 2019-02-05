package br.com.voxage.botbfs.state.empresa_operador;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inativo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INATIVO");
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "TERMINATE";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("TERMINATE", "/TERMINATE");
			}});
		}};
	}
}
