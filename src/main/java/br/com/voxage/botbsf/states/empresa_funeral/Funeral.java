package br.com.voxage.botbfs.state.empresa_funeral;

import java.util.Calendar;
import java.util.HashMap;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Funeral {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("FUNERAL");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				Calendar horaAtendimento = Calendar.getInstance();
				int hour = horaAtendimento.get(Calendar.HOUR_OF_DAY);
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(hour>18 || hour<8) {
					inputResult.setIntentName(BotBSF.STATES.PLANTAO);
				}else {
					inputResult.setIntentName(BotBSF.STATES.LOCAL);
				}
				
				botStateFlow.navigationKey = inputResult.getIntentName();
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.PLANTAO, "#PLANTAO");
				put(BotBSF.STATES.LOCAL, "#LOCAL");
			}});
		}};
	}
}
