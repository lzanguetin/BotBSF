package br.com.voxage.botbsf.states.empresa_inadimplencia;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaPago {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADPAGO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "DESPEDE";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>() {{
				put("DESPEDE", "#DESPEDE");
			}});
		}};
	}
}