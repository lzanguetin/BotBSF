 package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
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
				setMaxNoInput(2);
				setMaxInputTime(120000);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = OptionBuilder.optionBox("").addOption("1", "1 - Saber mais sobre o e-mail com assunto \"O seu boleto já está disponível\"")
							.addOption("2", "2 - Saber mais sobre o e-mail com assunto \"O seu boleto ainda não foi pago\"")
							.addOption("3", "3 - Saber mais sobre e-mail de inadimplência.").build();
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs().toLowerCase();
						
					switch(userInput) {
						case ("1 - saber mais sobre o e-mail com assunto \"o seu boleto já está disponível\""):
							try {
					                botInputResult.setIntentName(BotBSF.STATES.EMAILDISPONIVEL);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("2 - saber mais sobre o e-mail com assunto \"o seu boleto ainda não foi pago\""):
							try {
					                botInputResult.setIntentName(BotBSF.STATES.EMAILNPAGO);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("3 - saber mais sobre e-mail de inadimplência."):
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
					put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
	                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
				}});
		}};
	}
}
