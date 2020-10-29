package br.com.voxage.botbsf.states._start;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.Token;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Start {	
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("START");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				setMaxNoInput(2);
				setMaxInputTime(120000);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					bot.setFlagError(0);
					
					Message<?> message = null;
					message = OptionBuilder.optionBox("Olá, eu sou a Benê, atendente virtual da Gestora do Benefício Social Familiar!\n "
							+ "Selecione o Assunto para que eu possa te ajudar:").addOption("1", "Serviço Funeral").addOption("2", "Trabalhador ou Beneficiário").addOption("3", "Empregador").build();
					
					bot.addResponse(message);
		
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) -> {					
					BotInputResult botInputResult = new BotInputResult();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					botInputResult.setResult(BotInputResult.Result.OK);
												
					String userInput = userInputs.getConcatenatedInputs().toLowerCase();
						
					switch(userInput) {
						case ("serviço funeral"):
							try {
									dadosFluxo.setType("1");
					                botInputResult.setIntentName(BotBSF.STATES.FUNERAL);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("trabalhador ou beneficiário"):
							try {
									dadosFluxo.setType("2");
					                botInputResult.setIntentName(BotBSF.STATES.CPF);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						case ("empregador"):
							try {
									dadosFluxo.setType("3");
					                botInputResult.setIntentName(BotBSF.STATES.CNPJ);
					        }catch(Exception e) {
				                	botInputResult.setResult(BotInputResult.Result.ERROR);
				            }
							break;
						default:
								botInputResult.setResult(BotInputResult.Result.ERROR);
						}
					return botInputResult;
				});
				
				setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(()->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					Token customerInfo = null;
					String user = "voxage";
					String pass = "1234";
					String ip = bot.getUserSession().get("ip").toString();
					
					try {
						customerInfo = BotBSFIntegration.validarUsuario(bot, user, pass, ip);
						bot.setToken(customerInfo);
						botStateFlow.navigationKey = inputResult.getIntentName();
					}catch(Exception e) {
						botStateFlow.navigationKey = BotBSF.STATES.ATENDENTE;					
					}
					return botStateFlow;
				}));
				
				setNextNavigationMap(new HashMap<String, String>(){{
					put(BotBSF.STATES.CNPJ, "#CNPJ");
					put(BotBSF.STATES.CPF, "#CPF");
					put(BotBSF.STATES.FUNERAL, "#FUNERAL");
					put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
	                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
	                put("MAX_NO_INPUT", "/MAX_NO_INPUT"); 
				}});
		}};
	}
}
