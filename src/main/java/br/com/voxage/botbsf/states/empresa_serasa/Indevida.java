package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Indevida {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("INDEVIDA");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs();
						
					switch(userInput) {
						case "Empresa não pertence ao segmento abrangido pelo Benefício Social Familiar":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEGMENTO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "Empresa é do segmento que possui o BSF, mas ATUALMENTE não possui nenhum trabalhador registrado.":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEMTRAB);
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
					put(BotBSF.STATES.COBSEGMENTO, "#COBSEGMENTO");
					put(BotBSF.STATES.COBSEMTRAB, "#COBSEMTRAB");				
                    put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
