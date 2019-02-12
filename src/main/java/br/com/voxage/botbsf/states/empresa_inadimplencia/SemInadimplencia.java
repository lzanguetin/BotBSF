package br.com.voxage.botbsf.states.empresa_inadimplencia;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class SemInadimplencia {
	private static final String INITIAL_MESSAGE = "Não localizamos nenhum boleto em aberto para\nEmpresa: %s\nCNPJ: %s\nO último pagamento ocorreu em: %s.";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("SEMINAD");
			
			setBotStateInteractionType(BotStateInteractionType.NO_INPUT);

			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				String output = String.format(INITIAL_MESSAGE, consulta.getNome(), dadosFluxo.getCNPJ(), (consulta.getDebitos().getdataUltimoPagamento()));
				
				botState.setInitialMessage(output);
				
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
			}});
		}};
	}
}
