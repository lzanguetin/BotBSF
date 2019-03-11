package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Serasa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SERASA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;

				if((consulta.getSerasa().getSerasa()) == "true"){
					botStateFlow.navigationKey = "COMSERASA";
				}else if((consulta.getSerasa().getPre()) == "true"){
					botStateFlow.navigationKey = "PRESERASA";
				}else {
					botStateFlow.navigationKey = "SEMSERASA";
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.COMSERASA, "#COMSERASA");
				put(BotBSF.STATES.PRESERASA, "#PRESERASA");
				put(BotBSF.STATES.SEMSERASA, "#SEMSERASA");
			}});
		}};
	}
}
