package br.com.voxage.botbsf.states.trabalhador;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SegueCPF {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEGUECPF");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCPF consulta = bot.getConsultaCPF();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if(consulta.getUsuario() == "Trabalhador") {
					botStateFlow.navigationKey = "MENUTRABALHADOR";
				}else {
					botStateFlow.navigationKey = "MENUBENEFICIARIO";
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("MENUTRABALHADOR", "#MENUTRABALHADOR");
				put("MENUBENEFICIARIO", "#MENUBENEFICIARIO");
			}});
		}};
	}
}
