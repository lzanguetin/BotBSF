
package br.com.voxage.botbsf.states.empresa;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.ejb.entitties.SearchedLiveQuestion;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.lucenesearchengine.LuceneSearchEngine;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class MenuEmpresa {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("MENUEMPRESA");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				bot.setMenuType("Menu Empresa - ");
				
				Integer aux = bot.getFlagError();
				bot.setFlagError(aux+1);
				
				Message<?> message = null;
				message = OptionBuilder.optionBox("Vou te passar algumas opções e você escolhe aquela que melhor te ajudar.").
						addOption("1", "1 - Saber mais sobre o que é Benefício Social Familiar").addOption("2", "2 - Acesso do Operador ao Cadastro do Empregador").
						addOption("3", "3 - Atualizar os Dados do Empregador").addOption("4", "4 - Impressão ou Pagamento de Boletos").
						addOption("5", "5 - Inativação de Cadastro").addOption("6", "6 - Dúvidas sobre o e-mail recebido").
						addOption("7", "7 – Empregador Negativado no SERASA").addOption("8", "8 - Outros Assuntos").build();
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
				dadosFluxo.setFAQ(userInput);
				
				switch(userInput) {
				case "1 - saber mais sobre o que é benefício social familiar":
					try {
						bot.setOcorrencia("1 - Sobre BSF");
			            botInputResult.setIntentName(BotBSF.STATES.SOBRE);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "2 - acesso do operador ao cadastro do empregador":
					try {
						bot.setOcorrencia("2 - Acesso Operador");
			            botInputResult.setIntentName(BotBSF.STATES.OPERADOR);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "3 - atualizar os dados do empregador":
					try {
						bot.setOcorrencia("3 - Atualizar Dados");
			            botInputResult.setIntentName(BotBSF.STATES.ATUALIZAR);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "4 - impressão ou pagamento de boletos":
					try {
						bot.setOcorrencia("4 - Impressão Boleto");
			            botInputResult.setIntentName(BotBSF.STATES.BOLETOS);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "5 - inativação de cadastro":
					try {
						bot.setOcorrencia("5 - Inativação");
			            botInputResult.setIntentName(BotBSF.STATES.INATIVACAO);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "6 - dúvidas sobre o e-mail recebido":
					try {
						bot.setOcorrencia("6 - Dúvidas E-mail");
			            botInputResult.setIntentName(BotBSF.STATES.EMAIL);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "7 – empregador negativado no serasa":
					try {
						bot.setOcorrencia("7 - Empregador Negativado");
			            botInputResult.setIntentName(BotBSF.STATES.SERASA);
			        }catch(Exception e) {
		                botInputResult.setResult(BotInputResult.Result.ERROR);
		            }
					break;
				case "8 - outros assuntos":
					try {
						bot.setOcorrencia("8 - Outros Assuntos");
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
						setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
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
                        botInputResult.setIntentName(BotBSF.STATES.FAQ);
                    }
                }catch(Exception e) {
                	if(bot.getFlagError() < 3) {
                		setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
                		botInputResult.setResult(BotInputResult.Result.ERROR);
                	}else {
                		botInputResult.setIntentName("MAX_INPUT_ERROR");
                	}
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
				put(BotBSF.STATES.OPERADOR, "/OPERADOR");	
				put(BotBSF.STATES.ATUALIZAR, "/ATUALIZAR");	
				put(BotBSF.STATES.SOBRE, "/SOBRE");	
				put(BotBSF.STATES.BOLETOS, "/BOLETOS");	
				put(BotBSF.STATES.INATIVACAO, "/INATIVACAO");	
				put(BotBSF.STATES.EMAIL, "/EMAIL");	
				put(BotBSF.STATES.SERASA, "/SERASA");	
				put(BotBSF.STATES.OUTROS, "/OUTROS");
				put(BotBSF.STATES.MENUEMPRESA, "/MENUEMPRESA");
				put("faq", "#FAQ");
                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
