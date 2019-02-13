package br.com.voxage.botbsf.states.trabalhador;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

import java.util.Arrays;
import java.util.HashMap;

public class MenuTrabalhador {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Sobre qual asssunto deseja falar?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Acionar agora o Serviço Funeral\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Recebi esse cartão e quero saber o que é\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Acompanhar o andamento do meu benefício\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":4," + 
	           "         \"text\":\"Validar seu cartão de benefícios\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":5," + 
	           "         \"text\":\"Quero saber meu saldo no cartão Agilitas\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":6," + 
	           "         \"text\":\"Quer saber quando irá receber sua próxima Cesta de Alimentos\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":7," + 
	           "         \"text\":\"Outros Assuntos\"" + 
	           "      }," +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("MENUTRABALHADOR");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				bot.setLastState(BotBSF.STATES.MENUTRABALHADOR);
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
							botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "2":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CARTAO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "3":
						try {
							botInputResult.setIntentName(BotBSF.STATES.ANDAMENTO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "4":
						try {
							botInputResult.setIntentName(BotBSF.STATES.VALIDAR);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "5":
						try {
							botInputResult.setIntentName(BotBSF.STATES.SALDO);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "6":
						try {
							botInputResult.setIntentName(BotBSF.STATES.CESTA);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					case "7":
						try {
							botInputResult.setIntentName(BotBSF.STATES.OUTROS);
						}catch(Exception e) {
							botInputResult.setResult(BotInputResult.Result.ERROR);
						}
						break;
					default:
						botInputResult.setIntentName(BotBSF.STATES.FAQ);
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
				put(BotBSF.STATES.FUNERAL, "#CNPJ");
				put(BotBSF.STATES.CARTAO, "#CPF");
				put(BotBSF.STATES.ANDAMENTO, "#CNPJ");
				put(BotBSF.STATES.VALIDAR, "#CPF");	
				put(BotBSF.STATES.SALDO, "#CNPJ");
				put(BotBSF.STATES.CESTA, "#CPF");	
				put(BotBSF.STATES.OUTROS, "#CNPJ");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}