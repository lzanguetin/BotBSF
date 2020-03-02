package br.com.voxage.botbsf.states.empresa_email;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CNPJValidator;
import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.DadosEmpresa;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.botbsf.models.DadosOperador;
import br.com.voxage.chat.botintegration.message.Message;
import br.com.voxage.chat.botintegration.message.SimpleMessage;
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
			setMaxInputTime(120000);
			setMaxNoInput(2);
			
			setPreFunction(botState ->{
				BotStateFlow botStateFlow = new BotStateFlow();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				Message<?> initMsg = SimpleMessage.text("Digite o CPF do usuário que deve ter acesso a área do empregador");
				bot.addResponse(initMsg);
				
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
				DadosEmpresa consulta = bot.getDadosEmpresa();
				botStateFlow.flow = BotStateFlow.Flow.CONTINUE;
				
				DadosOperador customerInfo = null;
				
				try {
	                    customerInfo = BotBSFIntegration.dadosOperador(bot, dadosFluxo.getCNPJ(), dadosFluxo.getCPF());
	                    bot.setDadosOperador(customerInfo);
	                    DadosOperador op = bot.getDadosOperador();
	                    if("true".equals(op.getAtivo() )) {
	                    	if(consulta.getRegrasNegocio().getPossuiDebitos() == true) {
	                    		if(consulta.getRegrasNegocio().getPossuiApenasUmBoletoAVencer() == true) {
	                    			botStateFlow.navigationKey = BotBSF.STATES.INADVENCER;
	                    		}else {
	                    			botStateFlow.navigationKey = BotBSF.STATES.INADEBITO;
	                    		}
	    					}else {
	    						botStateFlow.navigationKey = BotBSF.STATES.SEMINAD;
	    					}
	                    }else{
	                    	botStateFlow.navigationKey = BotBSF.STATES.INADINATIVO;
	                    }
	               }catch(Exception e) {
	            	   botStateFlow.navigationKey = BotBSF.STATES.INADSCADASTRO;
	              }
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.INADATIVO, "#INADATIVO");
				put(BotBSF.STATES.INADEBITO, "#INADEBITO");
				put(BotBSF.STATES.INADVENCER, "#INADVENCER");
				put(BotBSF.STATES.SEMINAD, "#SEMINAD");
				put(BotBSF.STATES.INADINATIVO, "#INADINATIVO");
				put(BotBSF.STATES.INADSCADASTRO, "#INADSCADASTRO");
				put("TERMINATE", "/TERMINATE");
				put("MAX_INPUT_ERROR", "/MAX_INPUT_ERROR");
				put("MAX_NO_INPUT", "/MAX_NO_INPUT");
			}});
		}};
	}
}
