package br.com.voxage.botbsf.states.empresa_operador;

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

public class Ativo {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Localizei seu CPF como ativo para o CNPJ que você digitou %s.\nVocê esqueceu a senha?\"," + 
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
			setId("ATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				String output = String.format(INITIAL_MESSAGE, dadosFluxo.getCNPJ());
				
				botState.setInitialMessages(Arrays.asList(new BotMessage(output, MessageType.OPTION_BOX)));
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) -> {					
				BotInputResult botInputResult = new BotInputResult();
				botInputResult.setResult(BotInputResult.Result.OK);
											
				String userInput = userInputs.getConcatenatedInputs();
					
				switch(userInput) {
					case "1":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ESQUECEU);
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
				put(BotBSF.STATES.ESQUECEU, "#ESQUECEU");
				put(BotBSF.STATES.ATENDENTE, "/ATENDENTE");
			}});
		}};
	}
}
