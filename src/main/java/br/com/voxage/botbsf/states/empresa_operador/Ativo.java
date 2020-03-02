package br.com.voxage.botbsf.states.empresa_operador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.OptionBuilder;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Ativo {
	@SuppressWarnings({ "serial", "unused" })
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATIVO");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				DadosFluxo dados = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> message = null;
				message = OptionBuilder.optionBox(String.format("Localizei seu CPF como ativo no CNPJ/CPF informado. CNPJ/CPF %s,"
						+ "Empregador %s\n Você esqueceu a senha?", dados.getCNPJ(), consulta.getNomeEmpresa())).addOption("1", "Sim").addOption("2", "Não").build();
				bot.addResponse(message);
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) -> {					
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botInputResult.setResult(BotInputResult.Result.OK);
											
				String userInput = userInputs.getConcatenatedInputs().toLowerCase();
					
				switch(userInput) {
					case "sim":
						try {
							if(consulta.getEmailResponsavel() != null) {
								bot.insertTransition(10702);
								Message<?> message1 = null;
								message1 = SimpleMessage.text(String.format("Acabamos de enviar um e-mail para redefinição de sua senha para o(s) endereço(s): %s", consulta.getEmailResponsavel()));
								bot.addResponse(message1);
								
								Date data = new Date();
								SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
								Integer ret = bot.buildResult(dadosFluxo.getCPF(), dadosFluxo.getCNPJ(), "Bot", "Operador Ativo", "Acesso do Operador - Esqueceu a senha"
										, date.format(data).toString(), bot.getUserSession().get("call_id").toString(), bot);
								
								botInputResult.setIntentName(BotBSF.STATES.DESPEDE);
							}else {
								bot.insertTransition(10703);
								Message<?> message1 = null;
								message1 = SimpleMessage.text("Não identificamos no cadastro e-mail do EMPREGADOR");
								bot.addResponse(message1);
								botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
							}
				        }catch(Exception e) {
			                botInputResult.setResult(BotInputResult.Result.ERROR);
			            }
						break;
					case "não":
						try {
							bot.insertTransition(10701);
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
				put(BotBSF.STATES.DESPEDE, "#DESPEDE");
				put(BotBSF.STATES.ATENDENTE, "/ATENDENTE");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "MAX_NO_INPUT");
			}});
		}};
	}
}
