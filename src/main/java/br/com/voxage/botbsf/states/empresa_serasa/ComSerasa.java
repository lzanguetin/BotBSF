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

public class ComSerasa {
	private static final String INITIAL_MESSAGE = "{" + 
			   "   \"message\":\"Este CNPJ está negativado junto ao SERASA pelo BSF devido descumprimento de Convenção Coletiva de Trabalho. Sobre a Negativação:\"," +  
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Deseja entender por que a Empresa foi negativada\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Entende que essa cobrança é indevida\"" + 
	           "      }" +
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Deseja fazer um acordo para a regularização dos débitos\"" + 
	           "      }," + 
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("COMSERASA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				bot.setLastState(BotBSF.STATES.COMSERASA);
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
							botInputResult.setIntentName(BotBSF.STATES.NEGATIVADA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2":
						try {
							botInputResult.setIntentName(BotBSF.STATES.INDEVIDA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ACORDO);
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
				put(BotBSF.STATES.NEGATIVADA, "#NEGATIVADA");
				put(BotBSF.STATES.INDEVIDA, "#INDEVIDA");
				put(BotBSF.STATES.ACORDO, "#ACORDO");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}

		