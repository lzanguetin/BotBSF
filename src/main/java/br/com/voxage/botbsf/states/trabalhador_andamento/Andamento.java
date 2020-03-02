package br.com.voxage.botbsf.states.trabalhador_andamento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CurrencyValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.models.Beneficios;
import br.com.voxage.botbsf.models.DadosBeneficiario;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Andamento {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
				setId("ANDAMENTO");
				
				setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
				setMaxNoInput(2);
				setMaxInputTime(120000);
				
				setPreFunction(botState ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					
					if(bot.getFirstTime() == null) {
						Message<?> message = null;
						message = SimpleMessage.text("Digite o número do seu protocolo");
						bot.addResponse(message);
					}else if(bot.getFirstTime() == 0) {
						Message<?> message = null;
						message = SimpleMessage.text("Esse protocolo não está vinculado ao CPF que você digitou. Por favor informe "
								+ "novamente o número do protocolo");
						bot.addResponse(message);
					}
					
					if(bot.getFirstTime() == null) {
						bot.setFirstTime(0);
					}else {
						bot.setFirstTime(1);
					}
					
					return botStateFlow;
				});
				
				setProcessDirectInputFunction((botState, userInputs) ->{
					BotInputResult botInputResult = new BotInputResult();
					DadosBeneficiario consulta = bot.getDadosBeneficiario();
					DadosFluxo dadosFluxo = bot.getDadosFluxo();
					botInputResult.setResult(BotInputResult.Result.OK);
					
					String str = userInputs.getConcatenatedInputs();
					String userInput =  CurrencyValidator.getNumberDigitsOnly(str);
					dadosFluxo.setProt(userInput);
					
					List<Beneficios> benef = new ArrayList<Beneficios>();
					benef = consulta.getBeneficios();
					Integer aux = null;
					Integer count  = 0;
					
					Message<?> message = null;
					message = SimpleMessage.text("Aguarde enquanto eu consulto seu cadastro");
					bot.addResponse(message);
					
					for (Beneficios beneficios : benef) {
						int parseInt = 0;
						
						try {
							parseInt = Integer.parseInt(userInput);
							
							if(beneficios.getProtocolo() == parseInt) {
								aux = 1;
								count += count;
								bot.setPosBeneficio(count);
							}else {
								aux = 0;
								count += count;
								bot.setPosBeneficio(count);
							}
							
							if(aux == 1) {
								botInputResult.setIntentName(BotBSF.STATES.PROTOCOLO);
							}else {
								botInputResult.setIntentName(BotBSF.STATES.NPROTOCOLO);
							}
						}catch(NumberFormatException e){
							Message<?> msg = SimpleMessage.text("O campo informado não é um número, por favor, me informe o protocolo");
							bot.addResponse(msg);
							
							if(bot.getFirstTime() == 0) {
								bot.setFirstTime(1);
								botInputResult.setResult(BotInputResult.Result.ERROR);
							}else {
								botInputResult.setIntentName(BotBSF.STATES.ATENDENTE);
							}
						}
					}
					
					return botInputResult;
				});
				
				setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
					BotStateFlow botStateFlow = new BotStateFlow();
					botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
					botStateFlow.navigationKey = inputResult.getIntentName();
					
					return botStateFlow;
				}));
				
				setNextNavigationMap(new HashMap<String, String>(){{			
                    put(BotBSF.STATES.PROTOCOLO, "#PROTOCOLO");
                    put(BotBSF.STATES.NPROTOCOLO, "#NPROTOCOLO");
                    put(BotBSF.STATES.ATENDENTE, "#ATENDENTE");
	                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
	                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
				}});
		}};
	}
}