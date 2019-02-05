package br.com.voxage.botbsf.states.empresa;

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

public class MenuEmpresa {
	private static final String INITIAL_MESSAGE = "{" + 
	           "   \"message\":\"Vou te passar algumas opções e você define aquela que melhor te ajuda.\"," + 
	           "   \"options\":[" + 
	           "      {" + 
	           "         \"id\":1," + 
	           "         \"text\":\"Acionar Agora o Serviço Funeral\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":2," + 
	           "         \"text\":\"Acesso do Operador ao Cadastro da Empresa\"" + 
	           "      }," + 
	           "      {" + 
	           "         \"id\":3," + 
	           "         \"text\":\"Atualizar os Dados da Empresa\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":4," + 
	           "         \"text\":\"Saber Mais Sobre O Que É BSF\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":5," + 
	           "         \"text\":\"Impressão ou Pagamento de Boletos\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":6," + 
	           "         \"text\":\"Inativação de Cadastro\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":7," + 
	           "         \"text\":\"Recebeu e-mail de Inadimplência\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":8," + 
	           "         \"text\":\"Empresa está Negativada no SERASA\"" + 
	           "      }," +
	           "      {" + 
	           "         \"id\":9," + 
	           "         \"text\":\"Outros Assuntos\"" + 
	           "      }" +
	           "   ]" + 
	           "}";
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("MENUEMPRESA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setPreFunction(botState ->{
				bot.setLastState(BotBSF.STATES.CNPJ);
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
			            botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "2":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.OPERADOR);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "3":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.ATUALIZAR);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "4":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.SOBRE);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "5":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.BOLETOS);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "6":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.INATIVACAO);
			        }catch(Exception e) {
		               	botInputResult.setResult(BotInputResult.Result.ERROR);
			        }
					break;
				case "7":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.INADIMPLENCIA);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "8":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.NEGATIVADO);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "9":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.OUTROS);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				default:
					dadosFluxo.setFAQ(userInput);
					botInputResult.setIntentName(BotBSF.STATES.FAQ);
				}
			
			return botInputResult;
			});
			
			setPosFunction((botState, inputResult)->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				botStateFlow.navigationKey = inputResult.getIntentName();
				
				return botStateFlow;
			});
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.FUNERAL, "/FUNERAL");
				put(BotBSF.STATES.OPERADOR, "/OPERADOR");	
				put(BotBSF.STATES.ATUALIZAR, "/ATUALIZAR");	
				put(BotBSF.STATES.SOBRE, "/SOBRE");	
				put(BotBSF.STATES.BOLETOS, "/BOLETOS");	
				put(BotBSF.STATES.INATIVACAO, "/INATIVACAO");	
				put(BotBSF.STATES.INADIMPLENCIA, "/INADIMPLENCIA");	
				put(BotBSF.STATES.NEGATIVADO, "/NEGATIVADO");	
				put(BotBSF.STATES.OUTROS, "/OUTROS");
				put(BotBSF.STATES.FAQ, "/FAQ");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
