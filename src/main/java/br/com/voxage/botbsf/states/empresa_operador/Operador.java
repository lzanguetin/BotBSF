package br.com.voxage.botbsf.states.empresa_operador;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CNPJValidator;
import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.DadosOperador;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Operador {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("OPERADOR");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxNoInput(2);
			setMaxInputTime(120000);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				setInitialMessage("Digite o CPF do usuário que deve ter acesso a área do empregador.");
				
				return botStateFlow;
			});
			
			setProcessDirectInputFunction((botState, userInputs) -> {
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);			
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
				dadosFluxo.setCPF(userInput);
				
				if(((CPFValidator.isValidCPF(userInput)) == true) || ((CNPJValidator.isValidCNPJ(userInput)) == true)){
					dadosFluxo.setCPF(userInput);
					botInputResult.setResult(BotInputResult.Result.OK);
				}else {
					botInputResult.setResult(BotInputResult.Result.ERROR);
				}
				
				return botInputResult;
			});
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				DadosOperador customerInfo = null;
				
				Message<?> message = null;
				message = SimpleMessage.text("Aguarde enquanto eu consulto seu cadastro.");
				bot.addResponse(message);
				
				try {
						customerInfo = BotBSFIntegration.dadosOperador(bot, dadosFluxo.getCNPJ(), dadosFluxo.getCPF());
	                    bot.setDadosOperador(customerInfo);
	                    DadosOperador op = bot.getDadosOperador();
	                    if("true".equals(op.getAtivo())) {
	                    	dadosFluxo.setOperador("1");
	                    	botStateFlow.navigationKey = BotBSF.STATES.ATIVO;
	                    }else if("false".equals(op.getAtivo())) {
	                    	dadosFluxo.setOperador("2");
	                    	botStateFlow.navigationKey = BotBSF.STATES.INATIVO;
	                    }
	               }catch(Exception e) {
	            	   dadosFluxo.setOperador("3");
	            	   botStateFlow.navigationKey = BotBSF.STATES.SCADASTRO;
	              }
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.ATIVO, "#ATIVO");
				put(BotBSF.STATES.INATIVO, "#INATIVO");
				put(BotBSF.STATES.SCADASTRO, "#SCADASTRO");
                put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
                put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}