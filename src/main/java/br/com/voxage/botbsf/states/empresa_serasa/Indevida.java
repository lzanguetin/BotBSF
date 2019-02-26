package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Indevida {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Por que você considera a cobrança indevida?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Empresa não pertence ao segmento abrangido pelo Beneficio Social Familiar\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Empresa é do segmento que possui o BSF, mas atualmente não possui nenhum trabalhador registrado\"" + 
	           "      }" + 
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("INDEVIDA");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setPreFunction(botState -> {
	                bot.setLastState(BotBSF.STATES.INDEVIDA);
	                BotStateFlow botStateFlow = new BotStateFlow();
	                botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
	                
	                botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
	                
	                return botStateFlow;     
	            });
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs();
						
					switch(userInput) {
						case "1":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEGMENTO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "Empresa não pertence ao segmento abrangido pelo Beneficio Social Familiar":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEGMENTO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "2":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEMTRAB);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "Empresa é do segmento que possui o BSF, mas atualmente não possui nenhum trabalhador registrado":
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
