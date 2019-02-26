package br.com.voxage.botbsf.states.empresa;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.MessageType;
import br.com.voxage.chat.botintegration.entities.BotMessage;
import br.com.voxage.chat.botintegration.ejb.entitties.SearchedLiveQuestion;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
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
				dadosFluxo.setFAQ(userInput);
				
				switch(userInput) {
				case "1":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "Acionar Agora o Serviço Funeral":
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
				case "Acesso do Operador ao Cadastro da Empresa":
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
				case "Atualizar os Dados da Empresa":
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
				case "Saber Mais Sobre O Que É BSF":
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
				case "Impressão ou Pagamento de Boletos":
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
				case "Inativação de Cadastro":
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
				case "Recebeu e-mail de Inadimplência":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.INADIMPLENCIA);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "8":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.SERASA);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "Empresa está Negativada no SERASA":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.SERASA);
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
				case "Outros Assuntos":
					try {
			            botInputResult.setIntentName(BotBSF.STATES.OUTROS);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				default:
					try {
						userInput = dadosFluxo.getFAQ();						
						setBotStateInteractionType(BotStateInteractionType.FAQ_SEARCH);
						setNlpSearchEngine(new LuceneSearchEngine());
		                 
						return BotInputResult.BOT_INPUT_RESULT_RETRY;
					}catch(Exception ex) {
						botInputResult.setResult(BotInputResult.Result.ERROR);
					}	
				}
			
				return botInputResult;
			});
			
			setProcessFAQResultFunction((botState, input)->{
                BotInputResult botInputResult = new BotInputResult();
                botInputResult.setResult(br.com.voxage.vbot.BotInputResult.Result.OK);
                Type listType = new TypeToken<List<SearchedLiveQuestion>>(){}.getType();
                try {
                    List<SearchedLiveQuestion> results = new Gson().fromJson(input.getAnswer(), listType);
                    if(!results.isEmpty()) {
                        botInputResult.setAnswer(results.get(0).getChAnswer());
                    }
                }catch(Exception e) {
                    botInputResult.setResult(br.com.voxage.vbot.BotInputResult.Result.ERROR);
                }
                botInputResult.setIntentName(BotBSF.STATES.FAQ);
                
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
				put(BotBSF.STATES.SERASA, "/SERASA");	
				put(BotBSF.STATES.OUTROS, "/OUTROS");
				put(BotBSF.STATES.MENUEMPRESA, "/MENUEMPRESA");
				put("faq", "#FAQ");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
