package br.com.voxage.botbsf.states.global;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Redirect {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Resolvi sua Dúvida?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Sim\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Nâo\"" + 
	           "      }" + 
	           "   ]" + 
	           "}";
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("REDIRECT");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState -> {
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
				                botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
				        }catch(Exception e) {
			                	botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					case "2":
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
				put(BotBSF.STATES.DESPEDE, "#DESPEDE");
				put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");				
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
