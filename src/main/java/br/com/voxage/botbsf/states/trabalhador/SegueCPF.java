package br.com.voxage.botbsf.states.trabalhador;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SegueCPF {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEGUECPF");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = SimpleMessage.text("Pronto! Já localizei.");
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosBeneficiario consulta = bot.getDadosBeneficiario();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getTipo().equals("TRABALHADOR")) {
					botStateFlow.navigationKey = BotBSF.STATES.MENUTRABALHADOR;
				}else {
					botStateFlow.navigationKey = BotBSF.STATES.MENUBENEFICIARIO;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.MENUTRABALHADOR, "#MENUTRABALHADOR");
				put(BotBSF.STATES.MENUBENEFICIARIO, "#MENUBENEFICIARIO");
			}});
		}};
	}
}
