package br.com.voxage.botbsf.states.trabalhador_cesta;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Cesta {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("CESTA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs();
				dadosFluxo.setOS(userInput);
				
				return botInputResult;
			});
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCPF trab = bot.getConsultaCPF();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				if((trab.getCesta().getCestaReceber()) == "true") {
					botStateFlow.navigationKey = "POSSUICESTA";
				}else {
					botStateFlow.navigationKey = "NPOSSUICESTA";
				}

				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("POSSUICESTA", "/POSSUICESTA");
				put("NPOSSUICESTA", "/NPOSSUICESTA");
			}});
		}};
	}
}