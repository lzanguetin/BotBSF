package br.com.voxage.botbsf.states.empresa_serasa;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
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
				setMaxNoInput(2);
				setMaxInputTime(120000);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = OptionBuilder.optionBox("").addOption("1", "1 - Empregador não pertence ao segmento abrangido pelo "
							+ "Benefício Social Familiar").addOption("2", "2 - Empregador é do segmento que possui o Benefício Social "
									+ "Familiar, mas atualmente não possui nenhum trabalhador registrado").build();
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs().toLowerCase();
						
					switch(userInput) {
						case "1 - empregador não pertence ao segmento abrangido pelo benefício social familiar":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEGMENTO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "2 - empregador é do segmento que possui o benefício social familiar, mas atualmente não possui nenhum trabalhador registrado":
							try {
					                botInputResult.setIntentName(BotBSF.STATES.COBSEGMENTO);
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
					put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
					put("MAX_NO_INPUT", "/MAX_NO_INPUT");
				}});
		}};
	}
}
