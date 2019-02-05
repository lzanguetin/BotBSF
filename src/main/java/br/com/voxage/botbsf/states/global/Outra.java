package br.com.voxage.botbsf.states.global;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Outra {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Possuí outra dúvida?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Sim\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Não\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("OUTRA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs();
				
				switch(userInput) {
					case "1":
						try {
							if((dadosFluxo.getType()) == "1") {
								//botInputResult.setIntentName(BotBSF.STATES.EMPRESA);
							}else if((dadosFluxo.getType()) == "2") {
								//botInputResult.setIntentName(BotBSF.STATES.BENEFICIARIO);
							}
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.OK);
						}
						break;
					case "2":
						try {
							botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.OK);
						}
						break;
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
                //put(BotBSF.STATES.EMPRESA, "/TRABALHADOR");
                //put(BotBSF.STATES.BENEFICIARIO, "/REPRESENTANTE");
                put(BotBSF.STATES.DESPEDE, "/DESPEDE");
				put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
