package br.com.voxage.botbsf.states.empresa_inativacao;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inativacao {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"OK, escolha uma das opções a seguir\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"A Empresa não pertence ao segmento abrangido pelo Benefício Social Familiar.\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"A Empresa é do segmento, mas atualmente não possui trabalhador registrado.\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Empresa é do segmento, mas nunca teve trabalhador registrado.\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INATIVACAO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				bot.setLastState(BotBSF.STATES.INATIVACAO);
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs();
				
				switch(userInput) {
					case "1":
						try {
							botInputResult.setIntentName(BotBSF.STATES.NSEGMENTO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2":
						try {
							botInputResult.setIntentName(BotBSF.STATES.NTRABALHADOR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3":
						try {
							botInputResult.setIntentName(BotBSF.STATES.NREGISTRO);
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
			
			setNextNavigationMap(new HashMap<String,String>(){{
				put(BotBSF.STATES.NSEGMENTO, "#NSEGMENTO");
				put(BotBSF.STATES.NTRABALHADOR, "#NTRABALHADOR");
				put(BotBSF.STATES.NREGISTRO, "#NREGISTRO");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}