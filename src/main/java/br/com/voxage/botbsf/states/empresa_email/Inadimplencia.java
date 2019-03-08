package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.ConsultaOperador;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class Inadimplencia {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("INADIMPLENCIA");
			
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
				ConsultaCNPJ consulta = bot.getConsultaCNPJ();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				ConsultaOperador customerInfo = null;
				
				try {
	                    customerInfo = BotBSFIntegration.dadosOperador(bot, dadosFluxo.getCNPJ(), dadosFluxo.getCPF());
	                    bot.setConsultaOperador(customerInfo);
	                    ConsultaOperador op = bot.getConsultaOperador();
	                    if(op.getNome() == "true") {
	                    	if((consulta.getDebitos().getPossuiDeb()) == "true") {
	    						botStateFlow.navigationKey = "INADATIVO";
	    					}else if((consulta.getDebitos().getAvencer() == "true")) {
	    						botStateFlow.navigationKey = "INADVENCER";
	    					}else {
	    						botStateFlow.navigationKey = "SEMINAD";
	    					}
	                    }else if(op.getNome() == "false") {
	                    	botStateFlow.navigationKey = BotBSF.STATES.INADINATIVO;
	                    }
	               }catch(Exception e) {
	            	   botStateFlow.navigationKey = BotBSF.STATES.INADSCADASTRO;
	              }
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put("INADATIVO", "#INADATIVO");
				put("INADVENCER", "#INADVENCER");
				put("SEMINAD", "#SEMINAD");
				put("INADINATIVO", "#INADINATIVO");
				put("INADSCADASTRO", "#INADSCADASTRO");
				put("TERMINATE", "/TERMINATE");
				put("MAX_INPUT_ERROR", "/TERMINATE");
				put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
