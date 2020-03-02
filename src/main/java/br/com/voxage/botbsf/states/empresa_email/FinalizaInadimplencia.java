package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class FinalizaInadimplencia {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("FINALIZARINAD");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				setMaxNoInput(2);
				setMaxInputTime(120000);
			
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Message<?> message = null;
					message = OptionBuilder.optionBox("Resolvi sua dúvida?").addOption("1", "Sim").addOption("2", "Não").
							addOption("3", "Não, pois paguei meu boleto hoje e consta em aberto").build();
					bot.addResponse(message);
					
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) ->{
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
					
					String userInput = userInputs.getConcatenatedInputs().toLowerCase();
					
					switch(userInput) {
						case "sim":
							try {
								bot.insertTransition(12902);
								botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "não":
							try {
								bot.insertTransition(12901);
								botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "não, pois paguei meu boleto hoje e consta em aberto":
							try {
								bot.insertTransition(12903);
								Message<?> message = null;
								message = SimpleMessage.text("As baixas de pagamento ocorrem até 1 dia útil após a quitação bancária."
										+ "Neste caso acesse o site no próximo dia útil e caso o boleto permaneça em aberto contate-nos.");
								bot.addResponse(message);
								botInputResult.setIntentName(BotBSF.STATES.FINALIZAR);
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
	                put(BotBSF.STATES.FINALIZAR, "#FINALIZAR");
	                put(BotBSF.STATES.DESPEDE, "#DESPEDE");
					put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
					put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
	                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
				}});
		}};
	}
	
}
