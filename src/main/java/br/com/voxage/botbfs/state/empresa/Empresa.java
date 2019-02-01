package br.com.voxage.botbfs.state.empresa;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Empresa {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Sobre qual asssunto deseja falar?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Boleto Indisponível\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Dificuldade em Relacionar Trabalhadores\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Dificuldade Impressão de Boleto\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":4," + 
	           "         \"text\":\"Não Consegue Pagar Boleto\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":5," + 
	           "         \"text\":\"Acionamento Funeral\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":6," + 
	           "         \"text\":\"Cadastrar Empresa\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":7," + 
	           "         \"text\":\"Comunicação de Eventos\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":8," + 
	           "         \"text\":\"Andamento do Benefício\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":9," + 
	           "         \"text\":\"Validação do Cartão\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("REPRESENTANTE");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setPreFunction(botState -> {
	                bot.setLastState(BotBSF.STATES.START);
	                BotStateFlow botStateFlow = new BotStateFlow();
	                botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
	                
	                botState.setInitialMessages(Arrays.asList(new BotMessage(INITIAL_MESSAGE, MessageType.OPTION_BOX)));
	                
	                return botStateFlow;     
	            });
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs();
					
					return botInputResult;
				});
				
				setPosFunction((botState, inputResult) ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = inputResult.getIntentName();
					
					return botStateFlow;
				});
				
				setNextNavigationMap(new HashMap<String, String>(){{
                    put("MAX_INPUT_ERROR", "/TERMINATE");
                    put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
