package br.com.voxage.botbsf.states.empresa_operador;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inativo {
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				botState.setCustomField("cnpj", dados.getCNPJ());
				botState.setCustomField("empresa", consulta.getNome());
				botState.setCustomField("empresa", consulta.getEmail());
				
				return botStateFlow;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = "FINALIZAR";
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("FINALIZAR", "/FINALIZAR");
			}});
		}};
	}
}