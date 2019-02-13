package br.com.voxage.botbsf.states.trabalhador_cesta;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class PossuiCesta {
	private static final String INITIAL_MESSAGE = "Você receberá sua cesta %s até %s.";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("POSSUICESTA");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCPF trab = bot.getConsultaCPF();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				String output = String.format(INITIAL_MESSAGE, (trab.getCesta().getNumero()), (trab.getCesta().getDataRecebimento()));
				
				botState.setInitialMessage(output);
				
				return botStateFlow;
			});
			
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