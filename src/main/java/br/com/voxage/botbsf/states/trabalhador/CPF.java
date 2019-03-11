package br.com.voxage.botbsf.states.trabalhador;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CPFValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.ConsultaCPF;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

public class CPF {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("CPF");
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
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
				
				ConsultaCPF customerInfo = null;
				
				try {
					customerInfo = BotBSFIntegration.dadosTrabalhador(bot, dadosFluxo.getCPF());
					bot.setConsultaCPF(customerInfo);
					botStateFlow.navigationKey = BotBSF.STATES.SEGUECPF;
				}catch(Exception e) {
					botStateFlow.navigationKey = BotBSF.STATES.ERROCPF;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.SEGUECPF, "#SEGUECPF");
				put(BotBSF.STATES.ERROCPF, "#ERROCPF");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
