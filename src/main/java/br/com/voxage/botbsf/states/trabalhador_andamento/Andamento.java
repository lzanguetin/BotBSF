package br.com.voxage.botbsf.states.trabalhador_andamento;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Andamento {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("ANDAMENTO");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setProcessDirectInputFunction((botState, userInputs) ->{
					BotInputResult botInputResult = new BotInputResult();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					botInputResult.setResult(BotInputResult.Result.OK);
					
					String userInput = userInputs.getConcatenatedInputs();
					dadosFluxo.setProt(userInput);
										
					return botInputResult;
				});
				
				setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					ConsultaCPF consulta = bot.getConsultaCPF();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					if(consulta.getProtocolo() == dadosFluxo.getProt()) {
						botStateFlow.navigationKey = "PROTOCOLO";
					}else {
						botStateFlow.navigationKey = "NPROTOCOLO";
					}
					
					return botStateFlow;
				}));
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put("PROTOCOLO", "#PROTOCOLO");
                    put("NPROTOCOLO", "#NPROTOCOLO");
				}});
		}};
	}
}