package br.com.voxage.botbsf.states.empresa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;


public class SegueCNPJ {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEGUECNPJ");
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			setMaxInputError(3);
			setMaxNoInput(3);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = BotBSF.STATES.MENUEMPRESA;
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.MENUEMPRESA, "#MENUEMPRESA");
			}});
		}};
	}
}
