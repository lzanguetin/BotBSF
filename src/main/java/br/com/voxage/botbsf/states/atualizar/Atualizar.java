package br.com.voxage.botbfs.state.atualizar;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Atualizar {
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUALIZAR");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				switch(dadosFluxo.getOperador()) {
				case "1":
					 	botStateFlow.navigationKey = "ATUATIVO";
					 	break;
				case "2":
						botStateFlow.navigationKey = "ATUINATIVO";
						break;
				case "3":
						botStateFlow.navigationKey = "ATUSCADASTRO";
						break;
				default:
						botStateFlow.navigationKey = "TERMINATE";
				}
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap(new HashMap<String, String>()) {{
				put("ATUATIVO", "#ATUATIVO");
				put("ATUINATIVO", "#ATUINATIVO");
				put("ATUSCADASTRO", "#ATUSCADASTRO");
				put("TERMINATE", "/TERMINATE");
			}});
		}};
	}
}
