package br.com.voxage.botbsf.states.trabalhador_andamento;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Protocolo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("PROTOCOLO");
				
				setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					DadosBeneficiario consulta = bot.getDadosBeneficiario();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = SimpleMessage.text(consulta.getBeneficios().get(bot.getPosBeneficio()).getDetalhamento());
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = BotBSF.STATES.FINALIZAR;
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
				}});
		}};
	}
}