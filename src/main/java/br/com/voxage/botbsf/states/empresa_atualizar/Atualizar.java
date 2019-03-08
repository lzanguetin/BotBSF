package br.com.voxage.botbsf.states.empresa_atualizar;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.ConsultaOperador;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Atualizar {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("ATUALIZAR");
			
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setProcessDirectInputFunction((botState, userInputs) -> {
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);			
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
				dadosFluxo.setCPF(userInput);
				
				if((CPFValidator.isValidCPF(userInput)) == false) {
					botInputResult.setResult(BotInputResult.Result.ERROR);
				}else {
					dadosFluxo.setCPF(userInput);
					botInputResult.setResult(BotInputResult.Result.OK);
				}
				
				return botInputResult;
			});
			
			setAsyncPosFunction((botState, inputResult)-> CompletableFuture.supplyAsync(() ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				ConsultaOperador customerInfo = null;
				
				try {
	                    customerInfo = BotBSFIntegration.dadosOperador(bot, dadosFluxo.getCNPJ(), dadosFluxo.getCPF());
	                    bot.setConsultaOperador(customerInfo);
	                    ConsultaOperador op = bot.getConsultaOperador();
	                    if(op.getNome() == "true") {
	                    	dadosFluxo.setOperador("1");
	                    	botStateFlow.navigationKey = BotBSF.STATES.ATUATIVO;
	                    }else if(op.getNome() == "false") {
	                    	dadosFluxo.setOperador("2");
	                    	botStateFlow.navigationKey = BotBSF.STATES.ATUINATIVO;
	                    }
	               }catch(Exception e) {
	            	   dadosFluxo.setOperador("3");
	            	   botStateFlow.navigationKey = BotBSF.STATES.ATUSCADASTRO;
	              }
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>() {{
				put(BotBSF.STATES.ATUATIVO, "#ATUATIVO");
				put(BotBSF.STATES.ATUINATIVO, "#ATUINATIVO");
				put(BotBSF.STATES.ATUSCADASTRO, "#ATUSCADASTRO");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
