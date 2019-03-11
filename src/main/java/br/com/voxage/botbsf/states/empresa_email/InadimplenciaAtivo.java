package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class InadimplenciaAtivo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				botState.setCustomField("boletos", (consulta.getDebitos().getdebitosEmAberto()).toString());

				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "FINALIZARINAD";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>() {{
				put("FINALIZARINAD", "#FINALIZARINAD");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}