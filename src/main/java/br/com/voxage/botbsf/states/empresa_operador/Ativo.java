package br.com.voxage.botbsf.states.empresa_operador;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Ativo {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				botState.setCustomField("cnpj", dados.getCNPJ());
				botState.setCustomField("empresa", consulta.getNome());
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) -> {					
				BotInputResult botInputResult = new BotInputResult();
				botInputResult.setResult(BotInputResult.Result.OK);
											
				String userInput = userInputs.getConcatenatedInputs();
					
				switch(userInput) {
					case "Sim":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ESQUECEU);
				        }catch(Exception e) {
			                botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					case "Não":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
				        }catch(Exception e) {
			                botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					default:
							botInputResult.setResult(BotInputResult.Result.ERROR);
				}
				
				return botInputResult;
			});
			
			setPosFunction((botState, inputResult) ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.ESQUECEU, "#ESQUECEU");
				put(BotBSF.STATES.ATENDENTE, "/ATENDENTE");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
