package br.com.voxage.botbsf.states.global;

import java.util.Arrays;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Representante {
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
						
					switch(userInput) {
						case "1":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.BOLINDI);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "2":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.RELTRAB);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "3":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.IMPRBOL);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "4":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.PAGBOL);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "5":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.ACNFUN);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "6":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.CADEMP);
					        }catch(Exception e) {
				               	botInputResult.setResult(BotInputResult.Result.ERROR);
					        }
							break;
						case "7":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.COMENV);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "8":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.ANDABENF);
					        }catch(Exception e) {
				                botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case "9":
							try {
					            botInputResult.setIntentName(BotBSF.STATES.VALCARD);
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
					put(BotBSF.STATES.BOLINDI, "/BOLINDI");
					put(BotBSF.STATES.RELTRAB, "/RELTRAB");	
					put(BotBSF.STATES.IMPRBOL, "/IMPRBOL");	
					put(BotBSF.STATES.PAGBOL, "/PAGBOL");	
					put(BotBSF.STATES.ACNFUN, "/ACNFUN");	
					put(BotBSF.STATES.CADEMP, "/CADEMP");	
					put(BotBSF.STATES.COMENV, "/COMENV");	
					put(BotBSF.STATES.ANDABENF, "/ANDABENF");	
					put(BotBSF.STATES.VALCARD, "/VALCARD");
					put(BotBSF.STATES.FAQ, "/FAQ");
                    put("MAX_INPUT_ERROR", "/TERMINATE");
                    put("MAX_NO_INPUT", "/TERMINATE");
				}});
		}};
	}
}
