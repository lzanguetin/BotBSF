package br.com.voxage.botbsf.states._start;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Start {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("START");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs();
						
					switch(userInput) {
						case ("Empresa"):
							try {
									dadosFluxo.setType("1");
					                botInputResult.setIntentName(BotBSF.STATES.CNPJ);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("Trabalhador/Beneficiário"):
							try {
									dadosFluxo.setType("2");
					                botInputResult.setIntentName(BotBSF.STATES.CPF);
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
					put(BotBSF.STATES.CNPJ, "#CNPJ");
					put(BotBSF.STATES.CPF, "#CPF");				
					put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
