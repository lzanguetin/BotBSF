package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
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
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;

				if(consulta.getRegrasNegocio().getPossuiCadastroSerasa() == true){
					botStateFlow.navigationKey = BotBSF.STATES.COMSERASA;
				}else if(consulta.getRegrasNegocio().getPossuiCadastroPreSerasa() == true){
					botStateFlow.navigationKey = BotBSF.STATES.COMSERASA;
				}else {
					botStateFlow.navigationKey = BotBSF.STATES.SEMSERASA;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.COMSERASA, "#COMSERASA");
				put(BotBSF.STATES.SEMSERASA, "#SEMSERASA");
			}});
		}};
	}
}
