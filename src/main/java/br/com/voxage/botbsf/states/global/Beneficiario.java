package br.com.voxage.botbsf.states.global;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

import java.util.Arrays;
import java.util.HashMap;

public class Trabalhador {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Sobre qual asssunto deseja falar?\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Desbloqueio de Cartão\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Desconto em Folha\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Cartão de Identificação\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":4," + 
	           "         \"text\":\"Benefícios Prestados\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":5," + 
	           "         \"text\":\"Solicitar Benefício\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":6," + 
	           "         \"text\":\"Acompanhar Benefício\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":7," + 
	           "         \"text\":\"Comunicar Evento\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":8," + 
	           "         \"text\":\"Verificar Evento\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":9," + 
	           "         \"text\":\"O que é Benefício Social Familiar\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("TRABALHADOR");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				
				setPreFunction(botState ->{
					bot.setLastState(BotBSF.STATES.START);
					BotStateFlow botStateFlow =  new BotStateFlow();
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
								botInputResult.setIntentName(BotBSF.STATES.DESBCRT);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "2":
							try {
								botInputResult.setIntentName(BotBSF.STATES.DESCFOL);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "3":
							try {
								botInputResult.setIntentName(BotBSF.STATES.CRTID);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "4":
							try {
								botInputResult.setIntentName(BotBSF.STATES.BNFPREST);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "5":
							try {
								botInputResult.setIntentName(BotBSF.STATES.SOLBENEF);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "6":
							try {
								botInputResult.setIntentName(BotBSF.STATES.ACOMBENEF);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "7":
							try {
								botInputResult.setIntentName(BotBSF.STATES.COMEV);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "8":
							try {
								botInputResult.setIntentName(BotBSF.STATES.VEREV);
							}catch(Exception e) {
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}
							break;
						case "9":
							try {
								botInputResult.setIntentName(BotBSF.STATES.BSF);
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
					put(BotBSF.STATES.DESBCRT, "/DESBCRT");
					put(BotBSF.STATES.DESCFOL, "/DESCFOL");	
					put(BotBSF.STATES.CRTID, "/CRTID");	
					put(BotBSF.STATES.BNFPREST, "/BNFPREST");	
					put(BotBSF.STATES.SOLBENEF, "/SOLBENEF");	
					put(BotBSF.STATES.ACOMBENEF, "/ACOMBENEF");	
					put(BotBSF.STATES.COMEV, "/COMEV");	
					put(BotBSF.STATES.VEREV, "/VEREV");	
					put(BotBSF.STATES.BSF, "/BSF");
					put(BotBSF.STATES.FAQ, "/FAQ");
                    put("MAX_INPUT_ERROR", "/TERMINATE");
                    put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
