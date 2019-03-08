package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Email {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("EMAIL");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs();
						
					switch(userInput) {
						case ("O seu boleto já está disponível"):
							try {
					                botInputResult.setIntentName(BotBSF.STATES.EMAILDISPONIVEL);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("O seu boleto ainda não foi pago"):
							try {
					                botInputResult.setIntentName(BotBSF.STATES.EMAILNPAGO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("Recebeu E-mail de Inadimplência"):
							try {
					                botInputResult.setIntentName(BotBSF.STATES.INADIMPLENCIA);
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
					put(BotBSF.STATES.EMAILDISPONIVEL, "#EMAILDISPONIVEL");
					put(BotBSF.STATES.EMAILNPAGO, "#EMAILNPAGO");
					put(BotBSF.STATES.INADIMPLENCIA, "#INADIMPLENCIA");
					put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
