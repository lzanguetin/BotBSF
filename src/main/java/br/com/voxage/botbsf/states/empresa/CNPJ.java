package br.com.voxage.botbsf.states.empresa;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import br.com.voxage.basicvalidators.CNPJValidator;
import br.com.voxage.botbsf.BotBSF;
import br.com.voxage.botbsf.BotBSFIntegration;
import br.com.voxage.botbsf.models.ConsultaCNPJ;
import br.com.voxage.botbsf.models.DadosFluxo;
import br.com.voxage.vbot.BotInputResult;
import br.com.voxage.vbot.BotState;
import br.com.voxage.vbot.BotStateFlow;
import br.com.voxage.vbot.BotStateInteractionType;

@SuppressWarnings("unused")
public class CNPJ {
	@SuppressWarnings("serial")
	public static BotState load(BotBSF bot) {
		return new BotState("/") {{
			setId("CNPJ");
			setBotStateInteractionType(BotStateInteractionType.DIRECT_INPUT);
			setMaxInputTime(BotBSF.NO_INPUT_TIMEOUT);
			setMaxInputError(3);
			setMaxNoInput(3);
			
			setProcessDirectInputFunction((botState, userInputs) ->{
				BotInputResult botInputResult = new BotInputResult();
				DadosFluxo dadosFluxo = bot.getDadosFluxo();
				botInputResult.setResult(BotInputResult.Result.OK);
				String userInput = userInputs.getConcatenatedInputs().replaceAll("\\D+", "");;
				
				dadosFluxo.setCNPJ(userInput);
				
				if((CNPJValidator.isValidCNPJ(userInput)) == true) {
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
				
				ConsultaCNPJ customerInfo = null;
				
				try {
					customerInfo = BotBSFIntegration.dadosempresa(bot, dadosFluxo.getCNPJ());
					bot.setConsultaCNPJ(customerInfo);
					botStateFlow.navigationKey = BotBSF.STATES.SEGUECNPJ;
				}catch(Exception e) {
					botStateFlow.navigationKey = BotBSF.STATES.ERROCNPJ;
				}
				
				return botStateFlow;
			}));
			
			setNextNavigationMap(new HashMap<String, String>(){{
				put(BotBSF.STATES.SEGUECNPJ, "#SEGUECNPJ");
				put(BotBSF.STATES.ERROCNPJ, "#ERROCNPJ");
                put("MAX_INPUT_ERROR", "/TERMINATE");
                put("MAX_NO_INPUT", "/TERMINATE");
			}});
		}};
	}
}
